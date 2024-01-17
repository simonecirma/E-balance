document.addEventListener("DOMContentLoaded", function() {
    setTimeout(function() {
        document.querySelector('.container').classList.add('show');
        document.querySelector('.container').style.width = '700px';
        document.querySelector('h1').style.opacity = 1;
    }, 1000);
});