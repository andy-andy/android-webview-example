# README #

Play audio on page finished or resume application, and pause audio on pause application, if target web page implements javascript function 'playAudio' and 'pauseAudio'.

## JavaScript Function Example ##

```
#!javascript
var audio;
var playAudio = function() {
  audio.play();
}
var pauseAudio = function() {
  audio.pause();
}
$(document).ready(function() {
  audio = new Audio('./sample.mp3');
  audio.addEventListener('ended', function() {
    this.currentTime = 0;
    this.play();
  }, false);
});
```