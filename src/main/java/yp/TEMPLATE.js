var gg = [key]
var sl = [sleep]


for (let i = 0; i < gg.length; i++) {
    sleep(sl[i]);
    if (gg[i][0][2].length !== 0)
        gestures.apply(null, gg[i]);
}