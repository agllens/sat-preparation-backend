/* =========================
   Component Loader
   ========================= */
async function loadComponent(selector, path) {
  const el = document.querySelector(selector);
  if (!el) return false;

  try {
    const res = await fetch(path);
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    el.innerHTML = await res.text();
    return true;
  } catch (err) {
    console.error("Could not load component:", path, err);
    return false;
  }
}

/* =========================
   Hamburger Menu
   ========================= */
function initHamburger() {
  const btn       = document.getElementById("nav-toggle");
  const nav       = document.getElementById("site-nav");
  const iconOpen  = document.getElementById("icon-hamburger");
  const iconClose = document.getElementById("icon-close");

  if (!btn || !nav) return;

  function closeNav() {
    nav.classList.remove("is-open");
    btn.setAttribute("aria-expanded", "false");
    if (iconOpen)  iconOpen.style.display  = "block";
    if (iconClose) iconClose.style.display = "none";
  }

  btn.addEventListener("click", function () {
    const expanded = btn.getAttribute("aria-expanded") === "true";
    btn.setAttribute("aria-expanded", String(!expanded));
    nav.classList.toggle("is-open", !expanded);
    if (iconOpen)  iconOpen.style.display  = !expanded ? "none"  : "block";
    if (iconClose) iconClose.style.display = !expanded ? "block" : "none";
  });

  nav.querySelectorAll("a").forEach(link => link.addEventListener("click", closeNav));

  document.addEventListener("click", function (e) {
    if (!nav.contains(e.target) && !btn.contains(e.target)) closeNav();
  });
}

/* =========================
   SAT cards reveal
   ========================= */
function initSatCardsReveal() {
  const cards = document.querySelectorAll(".sat-card--reveal");
  if (!cards.length) return;

  const reduce = window.matchMedia("(prefers-reduced-motion: reduce)");
  if (reduce.matches) {
    cards.forEach(c => c.classList.add("is-visible"));
    return;
  }

  const io = new IntersectionObserver((entries) => {
    entries.forEach((e) => {
      if (!e.isIntersecting) return;
      e.target.classList.add("is-visible");
      io.unobserve(e.target);
    });
  }, { threshold: 0.18 });

  cards.forEach((c, i) => {
    c.style.transitionDelay = `${Math.min(i * 80, 160)}ms`;
    io.observe(c);
  });
}

/* =========================
   Results Carousel
   ========================= */
function initResultsCarousel() {
  const viewport = document.querySelector(".js-results-viewport");
  if (!viewport) return;

  const reducedMotionQuery = window.matchMedia("(prefers-reduced-motion: reduce)");
  const prefersReducedMotion = () => reducedMotionQuery.matches;

  let isDown = false;
  let startX = 0;
  let startScrollLeft = 0;

  let lastScrollLeft = 0;
  let lastTime = 0;
  let velocity = 0;
  let momentumRaf = null;

  const stopMomentum = () => {
    if (momentumRaf) cancelAnimationFrame(momentumRaf);
    momentumRaf = null;
  };

  const clampScroll = (left) => {
    const max = viewport.scrollWidth - viewport.clientWidth;
    if (left < 0) return 0;
    if (left > max) return max;
    return left;
  };

  const animateScrollTo = (targetLeft, duration = 260) => {
    stopMomentum();

    const from = viewport.scrollLeft;
    const to = clampScroll(targetLeft);
    if (Math.abs(to - from) < 1) return;

    if (prefersReducedMotion()) {
      viewport.scrollLeft = to;
      return;
    }

    const start = performance.now();
    const easeOut = (t) => 1 - Math.pow(1 - t, 3);

    const frame = (now) => {
      const progress = Math.min(1, (now - start) / duration);
      viewport.scrollLeft = from + (to - from) * easeOut(progress);
      if (progress < 1) requestAnimationFrame(frame);
    };

    requestAnimationFrame(frame);
  };

  const snapToNearest = () => {
    const firstCard = viewport.querySelector(".result-card");
    if (!firstCard) return;

    const gap = 34;
    const cardWidth = firstCard.getBoundingClientRect().width;
    const step = cardWidth + gap;

    const current = viewport.scrollLeft;
    const index = Math.round(current / step);
    animateScrollTo(index * step);
  };

  const startMomentum = () => {
    if (prefersReducedMotion()) {
      snapToNearest();
      return;
    }

    const friction = 0.95;
    const minVelocity = 0.02;

    const step = () => {
      const now = performance.now();
      const dt = Math.min(32, Math.max(8, now - lastTime || 16));
      lastTime = now;

      let next = viewport.scrollLeft + velocity * dt;
      const clamped = clampScroll(next);

      viewport.scrollLeft = clamped;

      if (clamped !== next) velocity = 0;
      else velocity *= friction;

      if (Math.abs(velocity) < minVelocity) {
        velocity = 0;
        momentumRaf = null;
        snapToNearest();
        return;
      }

      momentumRaf = requestAnimationFrame(step);
    };

    stopMomentum();
    lastTime = performance.now();
    momentumRaf = requestAnimationFrame(step);
  };

  const getPointerX = (e) => (e.touches ? e.touches[0].clientX : e.clientX);

  const onDown = (e) => {
    if (e.type === "mousedown" && e.button !== 0) return;
    isDown = true;
    stopMomentum();

    viewport.classList.add("is-dragging");
    startX = getPointerX(e);
    startScrollLeft = viewport.scrollLeft;

    lastScrollLeft = viewport.scrollLeft;
    lastTime = performance.now();
    velocity = 0;

    e.preventDefault();
  };

  const onMove = (e) => {
    if (!isDown) return;

    const x = getPointerX(e);
    const dx = x - startX;

    viewport.scrollLeft = startScrollLeft - dx;

    const now = performance.now();
    const dt = Math.max(8, now - lastTime);

    const current = viewport.scrollLeft;
    velocity = (current - lastScrollLeft) / dt;

    lastScrollLeft = current;
    lastTime = now;

    e.preventDefault();
  };

  const onUp = () => {
    if (!isDown) return;

    isDown = false;
    viewport.classList.remove("is-dragging");

    if (Math.abs(velocity) < 0.05) snapToNearest();
    else startMomentum();
  };

  viewport.addEventListener("mousedown", onDown);
  window.addEventListener("mousemove", onMove, { passive: false });
  window.addEventListener("mouseup", onUp);

  viewport.addEventListener("touchstart", onDown, { passive: false });
  viewport.addEventListener("touchmove", onMove, { passive: false });
  viewport.addEventListener("touchend", onUp);

  let scrollTimer = null;
  viewport.addEventListener("scroll", () => {
    if (viewport.classList.contains("is-dragging")) return;

    clearTimeout(scrollTimer);
    scrollTimer = setTimeout(snapToNearest, 120);
  });

  reducedMotionQuery.addEventListener?.("change", stopMomentum);
}

/* =========================
   Init (single entry point)
   ========================= */
document.addEventListener("DOMContentLoaded", async () => {
  const token = localStorage.getItem("token");

  if (token) {
    await loadComponent("#header-mount", "components/header_myprofile.html");
  } else {
    await loadComponent("#header-mount", "components/header.html");
  }

  await loadComponent("#footer-mount", "components/footer.html");

  initHamburger();        // ← запускается ПОСЛЕ того как шапка загружена
  initResultsCarousel();
  initSatCardsReveal();
});