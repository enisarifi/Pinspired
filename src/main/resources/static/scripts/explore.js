const postedImg = document.querySelectorAll('.posted-img');

postedImg.forEach(img => {
    const parent = img.parentElement;
    const overlay = parent.children[1];

    if(parent.className === "inner-img-container")
        img.addEventListener('mouseover', () => onPostedImgHover(img, overlay));
    overlay.addEventListener('mouseleave', () => onPostedImgLeave(img, overlay));
});

function onPostedImgHover(img, overlay) {
    img.style.filter = "brightness(80%)";
    overlay.style.display = "flex";
}

function onPostedImgLeave(img, overlay) {
    img.style.filter = "brightness(100%)";
    overlay.style.display = "none";
}
