<template>
    <v-app>
        <div class="text-center" id="call">
            <transition name="fade">
                <Loading v-bind:isConnecting="isConnecting" v-if="!start" />
            </transition>
            <header>
                <h1 class="font-weight-light mb-5 header">
                    Talk to your agent right now!
                </h1>
            </header>
            <audio id="globalAudioTag" autoplay></audio>
            <video id="largevideo" autoplay></video>
            <video id="globalVideoTag" autoplay style="display:none;"></video>
            <div class="text-center">
                <v-footer width="100%" fixed padless>
                    <v-btn
                        id="endcall"
                        :to="{ name: 'feedback' }"
                        height="58px"
                        width="100%"
                        x-large
                        depressed
                        tile
                        @click="endCall"
                    >
                        <h3>End Call</h3>
                    </v-btn>
                </v-footer>
            </div>
        </div>
    </v-app>
</template>

<script>
import rainbowSDK from "rainbow-web-sdk";
import Loading from "./Loading";
// import axios from "axios";

export default {
    name: "Call",
    components: { Loading },
    data: () => ({
        start: false,
        isConnecting: false,
        cancelled: false,
        call: ""
    }),
    mounted() {
        let self = this;
        self.checkCall();
        console.log(this.$store.state.agentId);
        if (this.$store.state.agentId != "") {
            self.isConnecting = true;
            self.startCall();
        } else {
            console.log("agent id empty");
        }
    },
    methods: {
        checkCall: function() {
            if (rainbowSDK.webRTC.canMakeAudioVideoCall()) {
                console.log("Browser supports calls");
            } else {
                console.log("Browser does not support calls");
            }
            if (rainbowSDK.webRTC.hasACamera()) {
                console.log("Browser supports video");
            } else {
                console.log("Browser does not support video");
            }
            if (rainbowSDK.webRTC.hasAMicrophone()) {
                console.log("Browser supports microphone");
            } else {
                console.log("Browser does not support microphone");
            }
            navigator.mediaDevices //authorise the application to access media device
                .getUserMedia({ audio: true, video: true })
                .then(function(stream) {
                    stream.getTracks().forEach(function(track) {
                        track.stop();
                    });
                    navigator.mediaDevices
                        .enumerateDevices()
                        .then(function(devices) {
                            devices.forEach(function(device) {
                                console.log(device);
                                if (device.deviceId === "default") {
                                    console.log(device);
                                    console.log(device.label, "is available");
                                    rainbowSDK.webRTC.useMicrophone("default");
                                    rainbowSDK.webRTC.useSpeaker("default");
                                    rainbowSDK.webRTC.useCamera("default");
                                }
                            });
                        })
                        .catch(function(error) {
                            console.log(error);
                        });
                })
                .catch(function(error) {
                    console.log(error);
                });
        },
        startCall: async function() {
            let self = this;
            try {
                console.log(this.$store.state.agentId);
                let contact = await rainbowSDK.contacts.searchById(
                    this.$store.state.agentId
                );
                console.log(contact);
                // let res = rainbowSDK.webRTC.callInAudio(contact);
                // if (res.label === "OK") {
                //     console.log("calling");
                // }
                let res = rainbowSDK.webRTC.callInVideo(contact);
                if (res.label === "OK") {
                    console.log("calling");
                }
                document.addEventListener(
                    rainbowSDK.webRTC.RAINBOW_ONWEBRTCERRORHANDLED,
                    self.onWebRTCErrorHandled
                );
                document.addEventListener(
                    rainbowSDK.webRTC.RAINBOW_ONWEBRTCCALLSTATECHANGED,
                    self.onWebRTCCallChanged
                );
                self.start = true;
            } catch (err) {
                console.log(err);
            }
        },
        onWebRTCCallChanged: function(event) {
            let self = this;
            self.call = event.detail;
            console.log("OnWebRTCCallChanged event", event.detail.status);
            console.log(self.call.status.value);
        },
        onWebRTCErrorHandled: function(event) {
            let errorSDK = event.detail;
            console.log("WebRTC ERROR: ", errorSDK);
        },
        endCall: async function() {
            let self = this;
            await rainbowSDK.webRTC.release(self.call);
            console.log("Session Ended");
        }
    }
};
</script>

<style scoped>
.call {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    text-align: center;
    padding: 20px;
}
.box {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    justify-items: center;
    align-content: center;
    overflow: auto;
}
.agent-text {
    text-align: center;
    position: absolute;
    width: 100%;
    margin-top: 150px;
}
.header {
    background-color: #f1f1f1;
    text-align: center;
    font-size: 60px;
    width: 100%;
    transition: 0.2s;
}
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
</style>
