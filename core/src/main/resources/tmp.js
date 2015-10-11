require = function e(t, s, n) {
    function i(o, r) {
        if (!s[o]) {
            if (!t[o]) {
                var c = "function" == typeof require && require;
                if (!r && c)return c(o, !0);
                if (a)return a(o, !0);
                var d = new Error("Cannot find module '" + o + "'");
                throw d.code = "MODULE_NOT_FOUND", d
            }
            var l = s[o] = {exports: {}};
            t[o][0].call(l.exports, function (e) {
                var s = t[o][1][e];
                return i(s ? s : e)
            }, l, l.exports, e, t, s, n)
        }
        return s[o].exports
    }

    for (var a = "function" == typeof require && require, o = 0; o < n.length; o++)i(n[o]);
    return i
}({
    "./client/diagnosticsPage/diagnosticsPage.jsx": [function (e, t) {
        var s = window.React, n = (window._, window.Isotope, e("./header/header.jsx")), i = e("./status/status.jsx"), a = e("./events/events.jsx"), o = e("./imu/imu.jsx"), r = e("./emgGraphs/emgGraphs.jsx"), c = e("./gestures/gestures.jsx"), d = e("./overrides/overrides.jsx"), l = e("./emg/emg.jsx"), u = e("./bluetooth/bluetooth.jsx"), m = e("./battery/battery.jsx"), g = (e("./recording/recording.jsx"), e("myo")), h = e("diag/sensor.store.js"), p = e("diag/event.store.js"), f = !1, v = s.createClass({
            displayName: "DiagnosticsPage",
            componentDidMount: function () {
                var e = this;
                $(this.refs.diagnosticsPage.getDOMNode()).isotope({
                    itemSelector: ".component",
                    layoutMode: "masonry"
                }), setTimeout(function () {
                    $(e.refs.diagnosticsPage.getDOMNode()).isotope("layout")
                }, 100), g.on("connected", function () {
                    f || (f = !0, h.setMyo(this), p.setMyo(this))
                }), g.on("disconnected", function () {
                    f = !1, this.off(), h.setMyo(g.create()), p.setMyo(g.create())
                }), g.onError = function () {
                    alert("There was an error with establishing the websocket. Try refreshing the page and/or restarting Myo Connect")
                }, g.connect("com.myojs.diagnostics")
            },
            render: function () {
                return s.DOM.div({
                    className: "diagnosticsPage",
                    ref: "diagnosticsPage"
                }, s.DOM.div({className: "background live"}), n(null), i(null), m(null), r(null), d(null), o(null), a(null), c(null), l(null), u(null))
            }
        });
        t.exports = v
    }, {
        "./battery/battery.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/battery/battery.jsx",
        "./bluetooth/bluetooth.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/bluetooth/bluetooth.jsx",
        "./emg/emg.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/emg/emg.jsx",
        "./emgGraphs/emgGraphs.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/emgGraphs/emgGraphs.jsx",
        "./events/events.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/events/events.jsx",
        "./gestures/gestures.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/gestures/gestures.jsx",
        "./header/header.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/header/header.jsx",
        "./imu/imu.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/imu/imu.jsx",
        "./overrides/overrides.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/overrides/overrides.jsx",
        "./recording/recording.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/recording/recording.jsx",
        "./status/status.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/status/status.jsx",
        "diag/event.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/event.store.js",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js",
        myo: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/myo/myo.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/battery/battery.jsx": [function (e, t) {
        var s = window.React, n = window._, i = e("classnames"), a = window.moment, o = e("diag/lines/lines.jsx"), r = e("diag/event.store.js"), c = 4, d = 10, l = s.createClass({
            displayName: "Battery",
            mixins: [r.mixin()],
            onStoreChange: function () {
                this.setState(r.getBattery())
            },
            getInitialState: function () {
                return r.getBattery()
            },
            createPoweredLines: function (e) {
                var t = c * d;
                return n.times(t, function (n) {
                    var a = n % d, o = Math.floor(n / d), r = e > n / t && (n + 1) / t >= e;
                    return s.DOM.div({
                        className: i("batteryBar", "bar" + a, "section" + o, {
                            selected: r,
                            unpowered: (n + 1) / t > e
                        }), key: n
                    })
                })
            },
            render: function () {
                var e, t = this.createPoweredLines(this.state.battery.val / 100);
                return this.state.battery.timestamp && (e = 100 == this.state.battery.val ? "Checking..." : "Last checked at " + a(this.state.battery.timestamp / 1e3).format("HH:mm:ss")), s.DOM.div({className: "battery component"}, s.DOM.h2(null, s.DOM.span(null, "Battery"), " ", s.DOM.span(null, "Life"), o.hr2(null), s.DOM.span({className: "percentage"}, this.state.battery.val, "%"), s.DOM.img({src: "/assets/diagnosticsPage/battery/battery.svg"})), s.DOM.div({className: "dataSection"}, s.DOM.img({
                    className: "emptyBattery",
                    src: "/assets/diagnosticsPage/battery/battery_empty.svg"
                }), t, s.DOM.div({className: "timestamp"}, e)))
            }
        });
        t.exports = l
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/event.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/event.store.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/bluetooth/bluetooth.jsx": [function (e, t) {
        var s = window.React, n = window._, i = (e("classnames"), e("diag/lines/lines.jsx")), a = e("diag/sensor.store.js"), o = s.createClass({
            displayName: "Bluetooth",
            mixins: [a.mixin()],
            onStoreChange: function () {
                this.setState(a.getBluetoothHistory())
            },
            getInitialState: function () {
                return a.getBluetoothHistory()
            },
            convertRSSIRange: function (e) {
                if (0 === e)return "0%";
                var t = -35, s = -100;
                return 1 + (e - t) / (t - s)
            },
            render: function () {
                var e = this, t = Math.round(100 * this.convertRSSIRange(n.last(this.state.bluetoothHistory))) + "%", a = n.map(this.state.bluetoothHistory, function (t, n) {
                    var i = 100 * e.convertRSSIRange(t) + "%";
                    return s.DOM.div({className: "bluetoothBar", style: {height: i}, key: n})
                });
                return s.DOM.div({className: "bluetooth component"}, s.DOM.h2(null, s.DOM.span(null, "Bluetooth"), " ", s.DOM.span(null, "Signal"), i.hr2(null), s.DOM.span({className: "bluetoothReading"}, t), s.DOM.img({src: "/assets/diagnosticsPage/bluetooth/bluetooth.svg"})), s.DOM.div({className: "barContainer"}, s.DOM.div({className: "legend"}), a))
            }
        });
        t.exports = o
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/emg/emg.jsx": [function (e, t) {
        var s = window.React, n = window._, i = (e("classnames"), e("diag/lines/lines.jsx")), a = e("diag/sensor.store.js"), o = s.createClass({
            displayName: "EMG",
            mixins: [a.mixin()],
            onStoreChange: function () {
                this.setState(a.getEMG())
            },
            getInitialState: function () {
                return a.getEMG()
            },
            render: function () {
                this.state.emg || (this.state.emg = [0, 0, 0, 0, 0, 0, 0, 0]);
                var e = n.map(this.state.emg, function (e, t) {
                    var n = Math.abs(e) / 128 * 100;
                    return s.DOM.div({className: "bar pod" + t, key: t}, s.DOM.div({
                        className: "data",
                        style: {height: n + "%"}
                    }))
                });
                return s.DOM.div({className: "emg component"}, i.hr3(null), s.DOM.h1(null, s.DOM.span(null, "EMG"), " ", s.DOM.span(null, "Data"), i.hr2(null), s.DOM.img({src: "/assets/diagnosticsPage/img/myo_shape.svg"})), s.DOM.img({
                    className: "myo",
                    src: "/assets/diagnosticsPage/emg/myo_image_live.png"
                }), e)
            }
        });
        t.exports = o
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/emgGraphs/emgGraphs.jsx": [function (e, t) {
        var s = window.React, n = window._, i = (e("classnames"), e("diag/lines/lines.jsx")), a = e("diag/lineGraph/lineGraph.jsx"), o = e("diag/sensor.store.js"), r = s.createClass({
            displayName: "EMGGraphs",
            mixins: [o.mixin()],
            onStoreChange: function () {
                this.setState(o.getEMGHistory())
            },
            getInitialState: function () {
                return o.getEMGHistory()
            },
            render: function () {
                var e = this, t = n.times(8, function (t) {
                    return a({
                        title: "pod" + t,
                        history: e.state.emgHistory[t],
                        resolution: 200,
                        key: t,
                        range: [-150, 150],
                        initialData: 0
                    })
                });
                return s.DOM.div({className: "emgGraphs component"}, i.hr3(null), s.DOM.h1(null, s.DOM.span(null, "EMG"), " ", s.DOM.span(null, "Graphs"), i.hr2(null), s.DOM.img({src: "/assets/diagnosticsPage/img/myo_shape.svg"})), s.DOM.div({className: "graphs"}, t))
            }
        });
        t.exports = r
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lineGraph/lineGraph.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lineGraph/lineGraph.jsx",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/events/events.jsx": [function (e, t) {
        var s = window.React, n = window._, i = (e("classnames"), e("diag/lines/lines.jsx")), a = e("diag/event.store.js"), o = s.createClass({
            displayName: "Events",
            mixins: [a.mixin()],
            onStoreChange: function () {
                this.setState(a.getEvents())
            },
            getInitialState: function () {
                return a.getEvents()
            },
            render: function () {
                var e = n.map(this.state.events, function (e, t) {
                    return s.DOM.div({
                        className: "event",
                        key: e.event + t
                    }, e.event, s.DOM.pre({className: "event_data"}, JSON.stringify(e.data, null, 2)))
                }).reverse();
                return s.DOM.div({className: "events component"}, i.hr3(null), s.DOM.h1(null, s.DOM.span(null, "Event"), " ", s.DOM.span(null, "Feed")), s.DOM.div({className: "eventsContainer"}, e))
            }
        });
        t.exports = o
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/event.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/event.store.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/gestures/gestures.jsx": [function (e, t) {
        var s = window.React, n = window._, i = e("classnames"), a = e("diag/lines/lines.jsx"), o = e("diag/event.store.js"), r = e("diag/sensor.store.js"), c = s.createClass({
            displayName: "Gestures",
            mixins: [o.mixin()],
            onStoreChange: function () {
                this.setState(o.getPose())
            },
            getInitialState: function () {
                return o.getPose()
            },
            render: function () {
                var e = "";
                "rest" !== this.state.pose && (e = this.state.pose);
                var t = "double_tap" === this.state.pose ? "_active" : "", n = "fingers_spread" === this.state.pose ? "_active" : "", i = "wave_out" === this.state.pose ? "_active" : "", o = "wave_in" === this.state.pose ? "_active" : "", r = "fist" === this.state.pose ? "_active" : "";
                return s.DOM.div({className: "gestures component"}, a.hr3(null), s.DOM.h1(null, s.DOM.span(null, "Gesture"), " ", s.DOM.span(null, "Feed"), a.hr2(null), s.DOM.img({src: "/assets/diagnosticsPage/gestures/hand.svg"})), s.DOM.div({className: "poses"}, s.DOM.img({src: "/assets/diagnosticsPage/gestures/pose_imgs/unlock_gesture" + t + ".png"}), s.DOM.img({src: "/assets/diagnosticsPage/gestures/pose_imgs/spread_fingers" + n + ".png"}), s.DOM.img({src: "/assets/diagnosticsPage/gestures/pose_imgs/wave_right" + i + ".png"}), s.DOM.img({src: "/assets/diagnosticsPage/gestures/pose_imgs/wave_left" + o + ".png"}), s.DOM.img({src: "/assets/diagnosticsPage/gestures/pose_imgs/make_fist" + r + ".png"})), s.DOM.div({className: "dataSection"}, s.DOM.div({className: "pose"}, s.DOM.span(null, "listening..."), " ", e)), d(null))
            }
        }), d = s.createClass({
            displayName: "StrengthBar", mixins: [r.mixin()], onStoreChange: function () {
                this.setState(r.getEMGHistory())
            }, getInitialState: function () {
                return r.getEMGHistory()
            }, numOfSections: 4, blocksPerSection: 3, getAverageStrength: function () {
                return r.getMyoInstance().synced ? n.reduce(this.state.emgHistory, function (e, t) {
                    var s = n.reduce(n.last(t, 10), function (e, t) {
                        return t = Math.abs(t), t > e ? t : e
                    }, 0);
                    return s > e ? s : e
                }, 0) / 128 : 0
            }, createStrengthBlocks: function (e) {
                var t = this, a = this.numOfSections * this.blocksPerSection;
                return n.times(a, function (n) {
                    var o = n % t.blocksPerSection, r = Math.floor(n / t.blocksPerSection), c = e > n / a && (n + 1) / a >= e;
                    return s.DOM.div({
                        className: i("strengthBlock", "block" + o, "section" + r, {
                            selected: c,
                            unpowered: (n + 1) / a > e
                        }), key: n
                    })
                })
            }, render: function () {
                var e = this.createStrengthBlocks(this.getAverageStrength());
                return s.DOM.div({className: "strengthBar"}, s.DOM.h2(null, "Gesture Strength"), e)
            }
        });
        t.exports = c
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/event.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/event.store.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/header/header.jsx": [function (e, t) {
        var s = window.React, n = (window._, e("classnames"), e("diag/lines/lines.jsx")), i = s.createClass({
            displayName: "Header",
            render: function () {
                var e = ["january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"], t = new Date, i = t.getHours() + "" + t.getMinutes();
                return s.DOM.div({className: "header component"}, n.hr3(null), s.DOM.div({className: "r titleContent"}, s.DOM.div({className: "c2"}, s.DOM.img({
                    className: "myoGlow",
                    src: "/assets/diagnosticsPage/img/myo_glow.png"
                })), s.DOM.div({className: "c8 o1"}, s.DOM.div({className: "hrShort"}), s.DOM.div({className: "date"}, s.DOM.span({className: "time"}, i, s.DOM.span({className: "h"}, "h")), s.DOM.span({className: "day"}, e[t.getMonth()], " ", t.getDate()), s.DOM.span({className: "year"}, t.getUTCFullYear())), s.DOM.p({className: "titleCopy"}, s.DOM.span({className: "light"}, "Myo"), "Diagnostics")), s.DOM.div({className: "c3 o1"}, s.DOM.div({className: "randomNumbers"}, s.DOM.span({className: "lightOrange"}, "0000"), s.DOM.span({className: "diagOrange"}, "4893"), s.DOM.br(null), s.DOM.span({className: "diagRed"}, "2389"), s.DOM.br(null), s.DOM.span({className: "lightRed"}, "2216")))), n.hr3(null), s.DOM.img({
                    className: "myoImage",
                    src: "/assets/diagnosticsPage/img/myo_image_live.png"
                }))
            }
        });
        t.exports = i
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/imu/cube.jsx": [function (e, t) {
        var s = window.React, n = (window._, e("classnames"), window.THREE, s.createClass({
            displayName: "Cube",
            getDefaultProps: function () {
                return {orientation: {x: 0, y: 0, z: 0, w: 1}}
            },
            getEulerAngles: function (e) {
                return {
                    roll: Math.atan2(2 * (e.y * e.z + e.w * e.x), e.w * e.w - e.x * e.x - e.y * e.y + e.z * e.z),
                    pitch: Math.asin(-2 * (e.x * e.z - e.w * e.y)),
                    yaw: Math.atan2(2 * (e.x * e.y + e.w * e.z), e.w * e.w + e.x * e.x - e.y * e.y - e.z * e.z)
                }
            },
            componentDidMount: function () {
                this.renderer = new THREE.WebGLRenderer({alpha: !0}), this.renderer.setSize(120, 120), $(this.refs.target.getDOMNode()).replaceWith(this.renderer.domElement), this.camera = new THREE.PerspectiveCamera(45, 1, 1, 1e3), this.camera.position.z = 500, this.scene = new THREE.Scene, this.cube = new THREE.Mesh(new THREE.BoxGeometry(200, 200, 200), new THREE.MeshNormalMaterial), this.cube.overdraw = !0, this.scene.add(this.cube), this.eulerAngles = {
                    yaw: 0,
                    pitch: 0,
                    roll: 0
                }, this.orientation = {x: 0, y: 0, z: 0, w: 0}, this.animate()
            },
            componentWillReceiveProps: function (e) {
                this.eulerAngles = this.getEulerAngles(e.orientation), this.orientation = e.orientation
            },
            animate: function () {
                var e = this;
                this.cube.setRotationFromQuaternion(new THREE.Quaternion(this.orientation.x, this.orientation.y, this.orientation.z, this.orientation.w)), this.renderer.render(this.scene, this.camera), requestAnimationFrame(function () {
                    e.animate()
                })
            },
            shouldComponentUpdate: function () {
                return !1
            },
            render: function () {
                return s.DOM.div({className: "Cube"}, s.DOM.div({ref: "target"}))
            }
        }));
        t.exports = n
    }, {classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/imu/imu.jsx": [function (e, t) {
        var s = window.React, n = (window._, e("classnames"), e("diag/lines/lines.jsx")), i = e("diag/lineGraph/lineGraph.jsx"), a = e("diag/sensor.store.js"), o = e("./cube.jsx"), r = s.createClass({
            displayName: "IMUGraphs",
            mixins: [a.mixin()],
            onStoreChange: function () {
                this.setState(a.getHistory())
            },
            getInitialState: function () {
                return a.getHistory()
            },
            render: function () {
                return s.DOM.div({className: "imuGraphs component"}, n.hr3(null), s.DOM.h1(null, s.DOM.span(null, "IMU"), " ", s.DOM.span(null, "Graphs"), n.hr2(null)), s.DOM.div({className: "graphs"}, i({
                    title: "gyroscope",
                    history: this.state.gyroscope,
                    range: [-600, 600],
                    initialData: {x: 0, y: 0, z: 0},
                    vertical: !0
                }), i({
                    title: "accelerometer",
                    history: this.state.accelerometer,
                    range: [-5, 5],
                    initialData: {x: 0, y: 0, z: 0},
                    vertical: !0
                }), i({
                    title: "orientation",
                    history: this.state.orientation,
                    range: [-1, 1],
                    initialData: {x: 0, y: 0, z: 0, w: 1},
                    vertical: !0
                })), o({orientation: a.getIMU().orientation}))
            }
        });
        t.exports = r
    }, {
        "./cube.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/imu/cube.jsx",
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lineGraph/lineGraph.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lineGraph/lineGraph.jsx",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/overrides/overrides.jsx": [function (e, t) {
        var s = window.React, n = (window._, e("classnames"), e("diag/lines/lines.jsx")), i = e("diag/sensor.store.js"), a = s.createClass({
            displayName: "Overrides",
            vibrate: function (e) {
                i.getMyoInstance().vibrate(e)
            },
            lockMode: function (e) {
                e ? i.getMyoInstance().lock() : i.getMyoInstance().unlock()
            },
            render: function () {
                return s.DOM.div({className: "overrides component"}, n.hr3(null), s.DOM.h1(null, s.DOM.span(null, "Manual"), " ", s.DOM.span(null, "Overrides"), n.hr2(null), s.DOM.img({src: "/assets/diagnosticsPage/overrides/alert.svg"})), s.DOM.div({className: "lock"}, s.DOM.div({className: "title"}, s.DOM.div({className: "text"}, s.DOM.img({src: "/assets/diagnosticsPage/overrides/lock.svg"}), s.DOM.span(null, "mode")), s.DOM.div({className: "lines"}, s.DOM.div({className: "upperLine"}), s.DOM.div({className: "lowerLine"}))), s.DOM.button({onClick: this.lockMode.bind(this, !1)}, "unlock"), s.DOM.button({onClick: this.lockMode.bind(this, !0)}, "Lock")), s.DOM.div({className: "vibrate"}, s.DOM.div({className: "title"}, s.DOM.div({className: "text"}, s.DOM.img({src: "/assets/diagnosticsPage/overrides/vibrate.svg"}), s.DOM.span(null, "Vibrate")), s.DOM.div({className: "lines"}, s.DOM.div({className: "upperLine"}), s.DOM.div({className: "lowerLine"}))), s.DOM.div({className: "lengthControl"}, s.DOM.button({onClick: this.vibrate.bind(this, "long")}, "Long"), s.DOM.div({className: "time"}, "100.5"), s.DOM.div({className: "units"}, "ms")), s.DOM.div({className: "lengthControl"}, s.DOM.button({onClick: this.vibrate.bind(this, "medium")}, "Medium"), s.DOM.div({className: "time"}, "45.6"), s.DOM.div({className: "units"}, "ms")), s.DOM.div({className: "lengthControl"}, s.DOM.button({onClick: this.vibrate.bind(this, "short")}, "Short"), s.DOM.div({className: "time"}, "23.5"), s.DOM.div({className: "units"}, "ms"))), o(null))
            }
        }), o = s.createClass({
            displayName: "OrientationBox", mixins: [i.mixin()], onStoreChange: function () {
                this.setState(i.getIMU())
            }, getInitialState: function () {
                return i.getIMU()
            }, zeroOrientation: function () {
                i.getMyoInstance().zeroOrientation()
            }, render: function () {
                return s.DOM.div({className: "orientation"}, s.DOM.div({className: "title"}, s.DOM.div({className: "text"}, s.DOM.img({src: "/assets/diagnosticsPage/overrides/orientation.svg"}), s.DOM.span(null, "Orientation")), s.DOM.div({className: "lines"}, s.DOM.div({className: "upperLine"}), s.DOM.div({className: "lowerLine"}))), s.DOM.div({className: "dataSection"}, s.DOM.div({className: "coord"}, s.DOM.div(null, "x: "), " ", s.DOM.span(null, this.state.orientation.x.toFixed(8))), s.DOM.div({className: "coord"}, s.DOM.div(null, "y: "), " ", s.DOM.span(null, this.state.orientation.y.toFixed(8))), s.DOM.div({className: "coord"}, s.DOM.div(null, "z: "), " ", s.DOM.span(null, this.state.orientation.z.toFixed(8))), s.DOM.div({className: "coord"}, s.DOM.div(null, "w: "), " ", s.DOM.span(null, this.state.orientation.w.toFixed(8)))), s.DOM.button({onClick: this.zeroOrientation}, "Set Origin"))
            }
        });
        t.exports = a
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx",
        "diag/sensor.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/recording/recording.jsx": [function (e, t) {
        var s = window.React, n = window.jQuery, i = (window._, e("classnames"), e("diag/lines/lines.jsx")), a = s.createClass({
            displayName: "Recording",
            getInitialState: function () {
                return {uploading: !1, frameCount: 0, recordedData: []}
            },
            startRecording: function () {
                this.props.setMode("recording"), this.setState({recordedData: []}), this.recordedData = []
            },
            componentDidMount: function () {
                console.log("mounting", window.location.href);
                var e = this;
                if (window.location.hash) {
                    var t = window.location.hash.slice(1);
                    console.log("getting data", t), n.get("http://dpaste.com/" + t + ".txt", function (t) {
                        e.recordedData = t, e.props.setMyoPlaybackState(e.recordedData[0])
                    })
                }
            },
            componentWillReceiveProps: function (e) {
                "recording" === e.mode && this.recordedData.push(n.extend(!0, {}, e.myoState))
            },
            stopRecording: function () {
                this.setState({frameCount: 0}), clearInterval(this.recordingTimer), this.props.setMyoPlaybackState(this.recordedData[0])
            },
            handleSlider: function (e) {
                this.setState({frameCount: e.target.value}), this.props.setMyoPlaybackState(this.recordedData[e.target.value]), console.log(e.target.value)
            },
            makeLive: function () {
                this.setState({frameCount: 0}), this.props.setMode("live")
            },
            uploadToDPaste: function () {
                var e = this;
                e.setState({uploading: !0}), console.log(2 * JSON.stringify(this.recordedData).length + "kb"), n.ajax({
                    url: "http://dpaste.com/api/v2/",
                    type: "POST",
                    data: {content: JSON.stringify(this.recordedData)},
                    success: function (t) {
                        var s = t.substr(t.lastIndexOf("/") + 1, t.length);
                        window.location = window.location.origin + "#" + s, e.setState({uploading: !1})
                    },
                    error: function () {
                    }
                })
            },
            render: function () {
                this.recordedData = this.recordedData || [];
                var e = 0;
                "recording" === this.props.mode ? e = 20 * this.recordedData.length / 1e3 : "playback" === this.props.mode && (e = 20 * this.state.frameCount / 1e3);
                var t = "upload";
                return this.state.uploading && (t = s.DOM.i({className: "fa fa-spin fa-spinner"})), s.DOM.div({className: "recording component"}, i.hr3(null), s.DOM.h1(null, s.DOM.span(null, "Data"), " ", s.DOM.span(null, "Recording"), i.hr2(null), s.DOM.img({src: "/assets/diagnosticsPage/gestures/hand.svg"})), s.DOM.button({onClick: this.startRecording}, "Record"), s.DOM.button({onClick: this.stopRecording}, "Stop Recording"), s.DOM.button({onClick: this.makeLive}, "Go live"), s.DOM.button({
                    className: "uploadButton",
                    onClick: this.uploadToDPaste
                }, t), s.DOM.input({
                    type: "range",
                    value: this.state.frameCount,
                    min: "0",
                    max: this.recordedData.length - 1,
                    onChange: this.handleSlider
                }), e)
            }
        });
        t.exports = a
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/client/diagnosticsPage/status/status.jsx": [function (e, t) {
        var s = window.React, n = (window._, e("classnames"), e("diag/lines/lines.jsx")), i = e("diag/event.store.js"), a = s.createClass({
            displayName: "Status",
            mixins: [i.mixin()],
            onStoreChange: function () {
                this.setState(i.getStatus())
            },
            getInitialState: function () {
                return i.getStatus()
            },
            render: function () {
                var e = this.state.status, t = s.DOM.div({className: "statusText connected"}, "connected");
                e.isSynced && (t = s.DOM.div({className: "statusText synced"}, "synced")), e.isConnected || (t = s.DOM.div({className: "statusText disconnected"}, "disconnected"));
                var i = s.DOM.img({
                    className: "lock",
                    src: "/assets/diagnosticsPage/status/locked.png"
                }), a = s.DOM.img({className: "unlock", src: "/assets/diagnosticsPage/status/unlocked.png"});
                e.isConnected && (e.isLocked ? i = s.DOM.img({
                    className: "lock",
                    src: "/assets/diagnosticsPage/status/locked_on.png"
                }) : a = s.DOM.img({className: "unlock", src: "/assets/diagnosticsPage/status/unlocked_on.png"}));
                var o = "", r = "";
                "right" == e.arm && (o = " selected"), "left" == e.arm && (r = " selected");
                var c = "on arm";
                return e.arm || (c = "lost"), s.DOM.div({className: "status component"}, s.DOM.div({className: "statusTag"}, "status"), s.DOM.div({className: "topSection"}, t, s.DOM.div({className: "macAddressSection"}, n.hr2(null), s.DOM.span(null, "myo"), s.DOM.span({className: "macAddress"}, e.macAddress || "00-00-00-00-00-00"))), s.DOM.div({className: "lockSection"}, s.DOM.div({className: "lockIcon"}, i, s.DOM.span(null, "locked")), s.DOM.div({className: "unlockIcon"}, a, s.DOM.span(null, "unlocked"))), s.DOM.div({className: "armSection"}, s.DOM.div({className: "myoCount"}, s.DOM.span(null, "MYO"), s.DOM.div({className: "count"}, e.connectIndex)), s.DOM.div({className: "armDirection"}, s.DOM.div({className: "left" + r}, s.DOM.div({className: "smallBar"}), s.DOM.div({className: "medBar"}), s.DOM.div({className: "largeBar"}), s.DOM.div({className: "direction"}, "L")), s.DOM.div({className: "right" + o}, s.DOM.div({className: "direction"}, "R"), s.DOM.div({className: "largeBar"}), s.DOM.div({className: "medBar"}), s.DOM.div({className: "smallBar"}))), s.DOM.div({className: "myoLocation"}, s.DOM.span(null, "myo"), s.DOM.span(null, c))))
            }
        });
        t.exports = a
    }, {
        classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js",
        "diag/event.store.js": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/event.store.js",
        "diag/lines/lines.jsx": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js": [function (e, t) {
        function s() {
            for (var e, t = "", n = 0; n < arguments.length; n++)if (e = arguments[n])if ("string" == typeof e || "number" == typeof e)t += " " + e; else if ("[object Array]" === Object.prototype.toString.call(e))t += " " + s.apply(null, e); else if ("object" == typeof e)for (var i in e)e.hasOwnProperty(i) && e[i] && (t += " " + i);
            return t.substr(1)
        }

        "undefined" != typeof t && t.exports && (t.exports = s)
    }, {}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/event.store.js": [function (e, t) {
        var s, n = window._, i = e("prism/flux.dispatcher"), a = e("prism/flux.warehouse"), o = {
            events: [],
            status: {},
            pose: null,
            battery: {val: 0, timestamp: null}
        }, r = function (e) {
            setTimeout(function () {
                e.requestBatteryLevel(), e.battery && 100 == e.battery.val && r(e)
            }, 500)
        };
        t.exports = EventStore = a.createStore(i, {
            setMyo: function (e) {
                var t = this;
                s = e, r(e), e.on("status", function (s) {
                    "rssi" != s.type && "bluetooth_strength" != s.type && (o.events.push({
                        event: s.type,
                        data: n.clone(s)
                    }), o.status = {
                        macAddress: e.macAddress,
                        arm: e.arm,
                        direction: e.direction,
                        isConnected: e.connected,
                        isLocked: e.locked,
                        isSynced: e.synced,
                        connectIndex: e.connectIndex
                    }, t.emitChange())
                }), e.on("pose", function (e) {
                    o.pose = e, t.emitChange()
                }), e.on("pose_off", function () {
                    o.pose = null, t.emitChange()
                }), e.on("battery_level", function (e, s) {
                    o.battery = {val: e, timestamp: s}, t.emitChange()
                }), o.status = {
                    macAddress: e.macAddress,
                    arm: e.arm,
                    direction: e.direction,
                    isConnected: e.connected,
                    isLocked: e.locked,
                    isSynced: e.synced,
                    connectIndex: e.connectIndex
                }, o.events = [], t.emitChange()
            }, getMyoInstance: function () {
                return s
            }, getStatus: function () {
                return {status: o.status}
            }, getEvents: function () {
                return {events: o.events}
            }, getBattery: function () {
                return {battery: o.battery}
            }, getPose: function () {
                return {pose: o.pose}
            }
        })
    }, {
        "prism/flux.dispatcher": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/flux.dispatcher.js",
        "prism/flux.warehouse": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/flux.warehouse.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lineGraph/lineGraph.jsx": [function (e, t) {
        var s = window.React, n = window._, i = e("classnames"), a = window.jQuery, o = (window.$.plot, s.createClass({
            displayName: "LineGraph",
            getDefaultProps: function () {
                return {resolution: 100, initialData: {x: 0, y: 0, z: 0}, vertical: !1}
            },
            componentDidMount: function () {
                var e = this;
                this.history = n.times(this.props.resolution, function () {
                    return e.props.initialData
                }), this.graph = a(this.refs.graph.getDOMNode()).plot(this.generateGraphData(), {
                    series: {shadowSize: 0},
                    colors: ["#04fbec", "#ebf1be", "#c14b2a", "#8aceb5"],
                    xaxis: this.getXAxisSettings(),
                    yaxis: this.getYAxisSettings(),
                    legend: {backgroundOpacity: 0},
                    grid: {borderColor: "#427F78", borderWidth: 1}
                }).data("plot")
            },
            componentWillReceiveProps: function (e) {
                this.history = e.history, this.updateGraph()
            },
            getXAxisSettings: function () {
                return this.props.vertical ? {min: this.props.range[0], max: this.props.range[1]} : {
                    show: !1,
                    min: 0,
                    max: this.props.resolution
                }
            },
            getYAxisSettings: function () {
                return this.props.vertical ? {show: !1, min: 0, max: this.props.resolution} : {
                    min: this.props.range[0],
                    max: this.props.range[1]
                }
            },
            shouldComponentUpdate: function () {
                return !1
            },
            updateGraph: function () {
                this.graph.setData(this.generateGraphData()), this.graph.draw()
            },
            orientData: function (e, t) {
                return this.props.vertical ? [t, e] : [e, t]
            },
            generateGraphData: function () {
                var e = this, t = {};
                return n.isObject(this.props.initialData) ? (n.each(this.history, function (s, i) {
                    n.isArray(s) && (s = n.map(s, function (e) {
                        return {g: e}
                    })), n.each(s, function (s, n) {
                        t[n] = t[n] || {label: n, data: []}, t[n].data.push(e.orientData(i, s))
                    })
                }), n.values(t)) : [n.map(this.history, function (t, s) {
                    return e.orientData(s, t)
                })]
            },
            render: function () {
                return s.DOM.div({className: i("lineGraph " + this.props.title, {vertical: this.props.vertical})}, s.DOM.div({className: "title"}, this.props.title), s.DOM.div({
                    className: "graph",
                    ref: "graph"
                }))
            }
        }));
        t.exports = o
    }, {classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/lines/lines.jsx": [function (e, t) {
        var s = window.React, n = (window._, e("classnames"), s.createClass({
            displayName: "hr3", render: function () {
                return s.DOM.div({className: "hr3"})
            }
        })), i = s.createClass({
            displayName: "hr2", render: function () {
                return s.DOM.div({className: "hr2"})
            }
        });
        t.exports = {hr3: n, hr2: i}
    }, {classnames: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/classnames/index.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/diag/sensor.store.js": [function (e, t) {
        var s, n, i = e("myo"), a = window._, o = e("prism/flux.dispatcher"), r = e("prism/flux.warehouse"), c = function () {
            return {
                history: {
                    bluetooth: a.times(42, function () {
                        return 0
                    }), orientation: a.times(100, function () {
                        return {x: 0, y: 0, z: 0, w: 0}
                    }), gyroscope: a.times(100, function () {
                        return {x: 0, y: 0, z: 0}
                    }), accelerometer: a.times(100, function () {
                        return {x: 0, y: 0, z: 0}
                    }), emg: a.times(8, function () {
                        return a.times(200, function () {
                            return 0
                        })
                    })
                },
                imu: {
                    orientation: {x: 0, y: 0, z: 0, w: 0},
                    gyroscope: {x: 0, y: 0, z: 0},
                    accelerometer: {x: 0, y: 0, z: 0}
                },
                bluetooth: 0,
                emg: [0, 0, 0, 0, 0, 0, 0, 0]
            }
        }, d = c(), l = !1, u = function (e) {
            e.on("imu", function (e) {
                d.history.gyroscope.push(e.gyroscope), d.history.gyroscope = d.history.gyroscope.slice(1), d.history.accelerometer.push(e.accelerometer), d.history.accelerometer = d.history.accelerometer.slice(1), d.history.orientation.push(e.orientation), d.history.orientation = d.history.orientation.slice(1), d.imu = e, l = !0
            }), e.on("rssi", function (e) {
                d.history.bluetooth.push(e), d.history.bluetooth = d.history.bluetooth.slice(1), d.bluetooth = e, l = !0
            }), e.on("emg", function (e) {
                d.emg = e, a.each(e, function (e, t) {
                    d.history.emg[t].push(e), d.history.emg[t] = d.history.emg[t].slice(1)
                }), l = !0
            })
        };
        t.exports = SensorStore = r.createStore(o, {
            setMyo: function (e) {
                var t = this;
                n = e, e.streamEMG(!0), setInterval(function () {
                    e.requestBluetoothStrength()
                }, 100), s = setInterval(function () {
                    l && (l = !1, t.emitChange())
                }, 50), e.on("disconnected", function () {
                    d = c(), t.emitChange()
                }), d = c(), t.emitChange(), u(e)
            }, getMyoInstance: function () {
                return n || i.create()
            }, getHistory: function () {
                return d.history
            }, getIMU: function () {
                return d.imu
            }, getEMG: function () {
                return {emg: d.emg}
            }, getEMGHistory: function () {
                return {emgHistory: d.history.emg}
            }, getBluetoothHistory: function () {
                return {bluetoothHistory: d.history.bluetooth}
            }
        })
    }, {
        myo: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/myo/myo.js",
        "prism/flux.dispatcher": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/flux.dispatcher.js",
        "prism/flux.warehouse": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/flux.warehouse.js"
    }],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/myo/myo.js": [function (e, t) {
        !function () {
            var s;
            "undefined" == typeof window ? s = e("ws") : ("WebSocket"in window || console.error("Myo.js : Sockets not supported :("), s = WebSocket), Myo = {
                defaults: {
                    api_version: 3,
                    socket_url: "ws://127.0.0.1:10138/myo/",
                    app_id: "com.myojs.default"
                }, lockingPolicy: "standard", events: [], myos: [], onError: function () {
                    throw"Myo.js had an error with the socket. Myo Connect might not be running. If it is, double check the API version."
                }, setLockingPolicy: function (e) {
                    return Myo.socket.send(JSON.stringify(["command", {
                        command: "set_locking_policy",
                        type: e
                    }])), Myo.lockingPolicy = e, Myo
                }, trigger: function (e) {
                    var t = Array.prototype.slice.apply(arguments).slice(1);
                    return i.trigger.call(Myo, Myo.events, e, t), Myo
                }, on: function (e, t) {
                    return i.on(Myo.events, e, t)
                }, off: function (e) {
                    return Myo.events = i.off(Myo.events, e), Myo
                }, connect: function (e) {
                    e && (Myo.defaults.app_id = e), Myo.socket = new s(Myo.defaults.socket_url + Myo.defaults.api_version + "?appid=" + Myo.defaults.app_id), Myo.socket.onmessage = Myo.handleMessage, Myo.socket.onopen = Myo.trigger.bind(Myo, "ready"), Myo.socket.onclose = Myo.trigger.bind(Myo, "socket_closed"), Myo.socket.onerror = Myo.onError
                }, disconnect: function () {
                    Myo.socket.close()
                }, handleMessage: function (e) {
                    var t = JSON.parse(e.data)[1];
                    t.type && "undefined" != typeof t.myo && ("paired" == t.type && Myo.myos.push(Myo.create({
                        macAddress: t.mac_address,
                        name: t.name,
                        connectIndex: t.myo
                    })), Myo.myos.map(function (e) {
                        if (e.connectIndex === t.myo) {
                            var s = !0;
                            n[t.type] && (s = n[t.type](e, t)), (!n[t.type] || s) && (e.trigger(t.type, t, t.timestamp), e.trigger("status", t, t.timestamp))
                        }
                    }))
                }, create: function (e) {
                    var t = a.merge({
                        macAddress: void 0,
                        name: void 0,
                        connectIndex: void 0,
                        locked: !0,
                        connected: !1,
                        synced: !1,
                        batteryLevel: 0,
                        lastIMU: void 0,
                        arm: void 0,
                        direction: void 0,
                        warmupState: void 0,
                        orientationOffset: {x: 0, y: 0, z: 0, w: 1},
                        events: []
                    }, e || {});
                    return a.merge(Object.create(Myo.methods), t)
                }, methods: {
                    trigger: function (e) {
                        var t = Array.prototype.slice.apply(arguments).slice(1);
                        return i.trigger.call(this, Myo.events, e, t), i.trigger.call(this, this.events, e, t), this
                    }, _trigger: function (e) {
                        var t = Array.prototype.slice.apply(arguments).slice(1);
                        return i.trigger.call(this, this.events, e, t), this
                    }, on: function (e, t) {
                        return i.on(this.events, e, t)
                    }, off: function (e) {
                        return this.events = i.off(this.events, e), this
                    }, lock: function () {
                        return Myo.socket.send(JSON.stringify(["command", {
                            command: "lock",
                            myo: this.connectIndex
                        }])), this
                    }, unlock: function (e) {
                        return Myo.socket.send(JSON.stringify(["command", {
                            command: "unlock",
                            myo: this.connectIndex,
                            type: e ? "hold" : "timed"
                        }])), this
                    }, zeroOrientation: function () {
                        return this.orientationOffset = a.quatInverse(this.lastQuant), this.trigger("zero_orientation"), this
                    }, vibrate: function (e) {
                        return e = e || "medium", Myo.socket.send(JSON.stringify(["command", {
                            command: "vibrate",
                            myo: this.connectIndex,
                            type: e
                        }])), this
                    }, requestBluetoothStrength: function () {
                        return Myo.socket.send(JSON.stringify(["command", {
                            command: "request_rssi",
                            myo: this.connectIndex
                        }])), this
                    }, requestBatteryLevel: function () {
                        return Myo.socket.send(JSON.stringify(["command", {
                            command: "request_battery_level",
                            myo: this.connectIndex
                        }])), this
                    }, streamEMG: function (e) {
                        return Myo.socket.send(JSON.stringify(["command", {
                            command: "set_stream_emg",
                            myo: this.connectIndex,
                            type: e ? "enabled" : "disabled"
                        }])), this
                    }
                }
            };
            var n = {
                pose: function (e, t) {
                    e.lastPose && (e.trigger(e.lastPose + "_off"), e.trigger("pose_off", e.lastPose)), "rest" == t.pose ? (e.trigger("rest"), e.lastPose = null, "standard" === Myo.lockingPolicy && e.unlock()) : (e.trigger(t.pose), e.trigger("pose", t.pose), e.lastPose = t.pose, "standard" === Myo.lockingPolicy && e.unlock(!0))
                }, orientation: function (e, t) {
                    e.lastQuant = t.orientation;
                    var s = a.quatRotate(e.orientationOffset, t.orientation), n = {
                        orientation: s,
                        accelerometer: {x: t.accelerometer[0], y: t.accelerometer[1], z: t.accelerometer[2]},
                        gyroscope: {x: t.gyroscope[0], y: t.gyroscope[1], z: t.gyroscope[2]}
                    };
                    e.lastIMU || (e.lastIMU = n), e.trigger("orientation", n.orientation, t.timestamp), e.trigger("accelerometer", n.accelerometer, t.timestamp), e.trigger("gyroscope", n.gyroscope, t.timestamp), e.trigger("imu", n, t.timestamp), e.lastIMU = n
                }, emg: function (e, t) {
                    e.trigger(t.type, t.emg, t.timestamp)
                }, arm_synced: function (e, t) {
                    return e.arm = t.arm, e.direction = t.x_direction, e.warmupState = t.warmup_state, e.synced = !0, !0
                }, arm_unsynced: function (e) {
                    return e.arm = void 0, e.direction = void 0, e.warmupState = void 0, e.synced = !1, !0
                }, connected: function (e, t) {
                    return e.connectVersion = t.version.join("."), e.connected = !0, !0
                }, disconnected: function (e) {
                    return e.connected = !1, !0
                }, locked: function (e) {
                    return e.locked = !0, !0
                }, unlocked: function (e) {
                    return e.locked = !1, !0
                }, warmup_completed: function (e) {
                    return e.warmupState = "warm", !0
                }, rssi: function (e, t) {
                    t.bluetooth_strength = a.getStrengthFromRssi(t.rssi), e.trigger("bluetooth_strength", t.bluetooth_strength, t.timestamp), e.trigger("rssi", t.rssi, t.timestamp), e.trigger("status", t, t.timestamp)
                }, battery_level: function (e, t) {
                    e.batteryLevel = t.battery_level, e.trigger("battery_level", t.battery_level, t.timestamp), e.trigger("status", t, t.timestamp)
                }
            }, i = {
                eventCounter: 0, trigger: function (e, t, s) {
                    var n = this;
                    return e.map(function (e) {
                        if (e.name == t && e.fn.apply(n, s), "*" == e.name) {
                            var i = s.slice(0);
                            i.unshift(t), e.fn.apply(n, i)
                        }
                    }), this
                }, on: function (e, t, s) {
                    var n = (new Date).getTime() + "" + i.eventCounter++;
                    return e.push({id: n, name: t, fn: s}), n
                }, off: function (e, t) {
                    return e = e.reduce(function (e, s) {
                        return s.name != t && s.id != t && t ? (e.push(s), e) : e
                    }, [])
                }
            }, a = {
                merge: function (e, t) {
                    for (var s in t)e[s] = t[s];
                    return e
                }, quatInverse: function (e) {
                    var t = Math.sqrt(e.x * e.x + e.y * e.y + e.z * e.z + e.w * e.w);
                    return {w: e.w / t, x: -e.x / t, y: -e.y / t, z: -e.z / t}
                }, quatRotate: function (e, t) {
                    return {
                        w: e.w * t.w - e.x * t.x - e.y * t.y - e.z * t.z,
                        x: e.w * t.x + e.x * t.w + e.y * t.z - e.z * t.y,
                        y: e.w * t.y - e.x * t.z + e.y * t.w + e.z * t.x,
                        z: e.w * t.z + e.x * t.y - e.y * t.x + e.z * t.w
                    }
                }, getStrengthFromRssi: function (e) {
                    var t = -95, s = -40;
                    return e = t > e ? t : e, e = e > s ? s : e, Math.round(100 * (e - t) / (s - t) * 100) / 100
                }
            };
            "undefined" != typeof t && (t.exports = Myo)
        }()
    }, {ws: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/myo/node_modules/ws/lib/browser.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/myo/node_modules/ws/lib/browser.js": [function (e, t) {
        function s(e, t) {
            var s;
            return s = t ? new i(e, t) : new i(e)
        }

        var n = function () {
            return this
        }(), i = n.WebSocket || n.MozWebSocket;
        t.exports = i ? s : null, i && (s.prototype = i.prototype)
    }, {}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/flux.dispatcher.js": [function (e, t) {
        var s = e("flux").Dispatcher;
        t.exports = new s
    }, {flux: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/node_modules/flux/index.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/flux.warehouse.js": [function (e, t) {
        var s = window._, n = e("events").EventEmitter, i = {fetching: !1, saving: !1, pending: !1, error: null};
        t.exports = Warehouse = {
            createStore: function (e, t) {
                var a = s.extend({
                    wrapRequest: function (e, t, s, n) {
                        var a = this;
                        if (n = n || 0, t = t || i, t.pending = !0, t.error = null, !t[e]) {
                            var o = function () {
                                t[e] = !0, t.pending = !1, s(function (s) {
                                    s && (t[e] = !1, t.error = s, a.emitChange()), t.pending ? o() : (t[e] = !1, a.emitChange())
                                }), a.emitChange()
                            };
                            0 === n ? o() : (clearInterval(t.timeout), t.timeout = setTimeout(function () {
                                o()
                            }, n)), this.emitChange()
                        }
                    }, handleAction: function (e) {
                        return "function" == typeof this.actions[e.type] && this.actions[e.type].call(this, e.payload), !0
                    }, mixin: function () {
                        var e = this;
                        return {
                            componentDidMount: function () {
                                if (!this.onStoreChange)throw new Error("Component is listening to store but doesn't have a 'onStoreChange' function");
                                e.addChangeListener(this.onStoreChange), this.onStoreChange()
                            }, componentWillUnmount: function () {
                                e.removeChangeListener(this.onStoreChange)
                            }
                        }
                    }, events: new n, emitChange: function () {
                        this.events.emit("CHANGE_EVENT")
                    }, addChangeListener: function (e) {
                        this.events.on("CHANGE_EVENT", e)
                    }, removeChangeListener: function (e) {
                        this.events.removeListener("CHANGE_EVENT", e)
                    }
                }, t);
                return a.events.setMaxListeners(0), a.dispatchToken = e.register(a.handleAction.bind(a)), a
            }
        }
    }, {events: "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/vitreum/node_modules/browserify/node_modules/events/events.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/node_modules/flux/index.js": [function (e, t) {
        t.exports.Dispatcher = e("./lib/Dispatcher")
    }, {"./lib/Dispatcher": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/node_modules/flux/lib/Dispatcher.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/node_modules/flux/lib/Dispatcher.js": [function (e, t) {
        "use strict";
        function s() {
            this.$Dispatcher_callbacks = {}, this.$Dispatcher_isPending = {}, this.$Dispatcher_isHandled = {}, this.$Dispatcher_isDispatching = !1, this.$Dispatcher_pendingPayload = null
        }

        var n = e("./invariant"), i = 1, a = "ID_";
        s.prototype.register = function (e) {
            var t = a + i++;
            return this.$Dispatcher_callbacks[t] = e, t
        }, s.prototype.unregister = function (e) {
            n(this.$Dispatcher_callbacks[e], "Dispatcher.unregister(...): `%s` does not map to a registered callback.", e), delete this.$Dispatcher_callbacks[e]
        }, s.prototype.waitFor = function (e) {
            n(this.$Dispatcher_isDispatching, "Dispatcher.waitFor(...): Must be invoked while dispatching.");
            for (var t = 0; t < e.length; t++) {
                var s = e[t];
                this.$Dispatcher_isPending[s] ? n(this.$Dispatcher_isHandled[s], "Dispatcher.waitFor(...): Circular dependency detected while waiting for `%s`.", s) : (n(this.$Dispatcher_callbacks[s], "Dispatcher.waitFor(...): `%s` does not map to a registered callback.", s), this.$Dispatcher_invokeCallback(s))
            }
        }, s.prototype.dispatch = function (e) {
            n(!this.$Dispatcher_isDispatching, "Dispatch.dispatch(...): Cannot dispatch in the middle of a dispatch."), this.$Dispatcher_startDispatching(e);
            try {
                for (var t in this.$Dispatcher_callbacks)this.$Dispatcher_isPending[t] || this.$Dispatcher_invokeCallback(t)
            } finally {
                this.$Dispatcher_stopDispatching()
            }
        }, s.prototype.isDispatching = function () {
            return this.$Dispatcher_isDispatching
        }, s.prototype.$Dispatcher_invokeCallback = function (e) {
            this.$Dispatcher_isPending[e] = !0, this.$Dispatcher_callbacks[e](this.$Dispatcher_pendingPayload), this.$Dispatcher_isHandled[e] = !0
        }, s.prototype.$Dispatcher_startDispatching = function (e) {
            for (var t in this.$Dispatcher_callbacks)this.$Dispatcher_isPending[t] = !1, this.$Dispatcher_isHandled[t] = !1;
            this.$Dispatcher_pendingPayload = e, this.$Dispatcher_isDispatching = !0
        }, s.prototype.$Dispatcher_stopDispatching = function () {
            this.$Dispatcher_pendingPayload = null, this.$Dispatcher_isDispatching = !1
        }, t.exports = s
    }, {"./invariant": "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/node_modules/flux/lib/invariant.js"}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/prism/node_modules/flux/lib/invariant.js": [function (e, t) {
        "use strict";
        var s = function (e, t, s, n, i, a, o, r) {
            if (!e) {
                var c;
                if (void 0 === t)c = new Error("Minified exception occurred; use the non-minified dev environment for the full error message and additional helpful warnings."); else {
                    var d = [s, n, i, a, o, r], l = 0;
                    c = new Error("Invariant Violation: " + t.replace(/%s/g, function () {
                            return d[l++]
                        }))
                }
                throw c.framesToPop = 1, c
            }
        };
        t.exports = s
    }, {}],
    "/tmp/build_5ece7ca987e53ca5743dd0f281c37d50/node_modules/vitreum/node_modules/browserify/node_modules/events/events.js": [function (e, t) {
        function s() {
            this._events = this._events || {}, this._maxListeners = this._maxListeners || void 0
        }

        function n(e) {
            return "function" == typeof e
        }

        function i(e) {
            return "number" == typeof e
        }

        function a(e) {
            return "object" == typeof e && null !== e
        }

        function o(e) {
            return void 0 === e
        }

        t.exports = s, s.EventEmitter = s, s.prototype._events = void 0, s.prototype._maxListeners = void 0, s.defaultMaxListeners = 10, s.prototype.setMaxListeners = function (e) {
            if (!i(e) || 0 > e || isNaN(e))throw TypeError("n must be a positive number");
            return this._maxListeners = e, this
        }, s.prototype.emit = function (e) {
            var t, s, i, r, c, d;
            if (this._events || (this._events = {}), "error" === e && (!this._events.error || a(this._events.error) && !this._events.error.length)) {
                if (t = arguments[1], t instanceof Error)throw t;
                throw TypeError('Uncaught, unspecified "error" event.')
            }
            if (s = this._events[e], o(s))return !1;
            if (n(s))switch (arguments.length) {
                case 1:
                    s.call(this);
                    break;
                case 2:
                    s.call(this, arguments[1]);
                    break;
                case 3:
                    s.call(this, arguments[1], arguments[2]);
                    break;
                default:
                    for (i = arguments.length, r = new Array(i - 1), c = 1; i > c; c++)r[c - 1] = arguments[c];
                    s.apply(this, r)
            } else if (a(s)) {
                for (i = arguments.length, r = new Array(i - 1), c = 1; i > c; c++)r[c - 1] = arguments[c];
                for (d = s.slice(), i = d.length, c = 0; i > c; c++)d[c].apply(this, r)
            }
            return !0
        }, s.prototype.addListener = function (e, t) {
            var i;
            if (!n(t))throw TypeError("listener must be a function");
            if (this._events || (this._events = {}), this._events.newListener && this.emit("newListener", e, n(t.listener) ? t.listener : t), this._events[e] ? a(this._events[e]) ? this._events[e].push(t) : this._events[e] = [this._events[e], t] : this._events[e] = t, a(this._events[e]) && !this._events[e].warned) {
                var i;
                i = o(this._maxListeners) ? s.defaultMaxListeners : this._maxListeners, i && i > 0 && this._events[e].length > i && (this._events[e].warned = !0, console.error("(node) warning: possible EventEmitter memory leak detected. %d listeners added. Use emitter.setMaxListeners() to increase limit.", this._events[e].length), "function" == typeof console.trace && console.trace())
            }
            return this
        }, s.prototype.on = s.prototype.addListener, s.prototype.once = function (e, t) {
            function s() {
                this.removeListener(e, s), i || (i = !0, t.apply(this, arguments))
            }

            if (!n(t))throw TypeError("listener must be a function");
            var i = !1;
            return s.listener = t, this.on(e, s), this
        }, s.prototype.removeListener = function (e, t) {
            var s, i, o, r;
            if (!n(t))throw TypeError("listener must be a function");
            if (!this._events || !this._events[e])return this;
            if (s = this._events[e], o = s.length, i = -1, s === t || n(s.listener) && s.listener === t)delete this._events[e], this._events.removeListener && this.emit("removeListener", e, t); else if (a(s)) {
                for (r = o; r-- > 0;)if (s[r] === t || s[r].listener && s[r].listener === t) {
                    i = r;
                    break
                }
                if (0 > i)return this;
                1 === s.length ? (s.length = 0, delete this._events[e]) : s.splice(i, 1), this._events.removeListener && this.emit("removeListener", e, t)
            }
            return this
        }, s.prototype.removeAllListeners = function (e) {
            var t, s;
            if (!this._events)return this;
            if (!this._events.removeListener)return 0 === arguments.length ? this._events = {} : this._events[e] && delete this._events[e], this;
            if (0 === arguments.length) {
                for (t in this._events)"removeListener" !== t && this.removeAllListeners(t);
                return this.removeAllListeners("removeListener"), this._events = {}, this
            }
            if (s = this._events[e], n(s))this.removeListener(e, s); else for (; s.length;)this.removeListener(e, s[s.length - 1]);
            return delete this._events[e], this
        }, s.prototype.listeners = function (e) {
            var t;
            return t = this._events && this._events[e] ? n(this._events[e]) ? [this._events[e]] : this._events[e].slice() : []
        }, s.listenerCount = function (e, t) {
            var s;
            return s = e._events && e._events[t] ? n(e._events[t]) ? 1 : e._events[t].length : 0
        }
    }, {}]
}, {}, []);