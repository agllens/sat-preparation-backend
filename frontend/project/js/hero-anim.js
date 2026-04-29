(() => {
  const area = document.querySelector(".scores-area");
  if (!area) return;

  const bubbles = Array.from(area.querySelectorAll(".bubble"));
  const strength = 6; // SMALL (was 18)

  let raf = null;
  let lastX = 0, lastY = 0;

  function clamp(v, min, max){ return Math.max(min, Math.min(max, v)); }

  function apply() {
    raf = null;
    bubbles.forEach((b, i) => {
      const k = (i % 4 + 1) / 4;
      let tx = lastX * strength * k;
      let ty = lastY * strength * k;

      // prevent extreme moves
      tx = clamp(tx, -6, 6);
      ty = clamp(ty, -6, 6);

      b.style.transform = `translate(${tx}px, ${ty}px)`;
    });
  }

  area.addEventListener("mousemove", (e) => {
    const rect = area.getBoundingClientRect();
    lastX = (e.clientX - rect.left) / rect.width - 0.5;
    lastY = (e.clientY - rect.top) / rect.height - 0.5;
    if (!raf) raf = requestAnimationFrame(apply);
  });

  area.addEventListener("mouseleave", () => {
    bubbles.forEach((b) => (b.style.transform = ""));
  });
})();