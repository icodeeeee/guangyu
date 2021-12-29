
var ggs = [keys]
var vvs = [sleeps]


for (let i = 0; i >=0; i++) {
    var index = Math.floor(Math.random()*ggs.length)

    var gg = ggs[index]
    var sl = vvs[index]


    for (let i = 0; i < gg.length; i++) {
        sleep(sl[i]);
        if (gg[i][0][2].length !== 0)
            gestures.apply(null, gg[i]);
    }
}
