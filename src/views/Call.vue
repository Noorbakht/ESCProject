<template>
  <div class="call" id="call">
    <header class="header">
      Header
      <audio id="globalAudioTag" autoplay />
      <!--to allow customer to receive audio from agent-->
    </header>
  </div>
</template>

<script>
import rainbowSDK from "rainbow-web-sdk";
// import axios from "axios";

export default {
  name: "Call",
  props: ["agentId"],
  components: {},
  data: () => ({
    start: false,
    connecting: false,
    cancelled: false,
    call: "",
    exit: false
  }),
  mounted() {
    console.log(this.$route.params.agentId);
    let self = this;
    self.checkCall();
    if (self.agentId === "") {
      console.log("trying");
    } else {
      self.connecting = true;
      self.startCall();
    }
  },
  methods: {
    checkCall: function() {
      if (rainbowSDK.webRTC.canMakeAudioVideoCall()) {
        console.log("Browser supports calls");
      } else {
        console.log("Browser does not support calls");
      }
      navigator.mediaDevices //authorise the application to access media device
        .getUserMedia({ audio: true })
        .then(function(stream) {
          stream.getTracks().forEach(function(track) {
            track.stop();
          });
          navigator.mediaDevices
            .enumerateDevices()
            .then(function(devices) {
              devices.forEach(function(device) {
                if (device.deviceId === "default") {
                  console.log(device);
                  console.log(device.label, "is available");
                }
              });
            })
            .catch(function(error) {
              console.log(error);
            });
          rainbowSDK.webRTC.useMicrophone("default");
          rainbowSDK.webRTC.useSpeaker("default");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    startCall: async function() {
      let self = this;
      try {
        console.log(this.$route.params.agentId);
        let contact = await rainbowSDK.contacts.searchById(
          this.$route.params.agentId
        );
        var res = rainbowSDK.webRTC.callInAudio(contact); //start to call the contact with available agent
        if (res.label === "OK") {
          console.log("calling");
        }
        self.start = true;
        document.addEventListener(
          rainbowSDK.webRTC.RAINBOW_ONWEBRTCCALLSTATECHANGED,
          self.onWebRTCCallChanged
        );
      } catch (err) {
        console.log(err);
      }
    },
    onWebRTCCallChanged: async function(event) {
      let self = this;
      self.call = event.detail;
      //console.log("OnWebRTCCallChanged event", event.detail.status);
      if (event.detail.status.value === "Unknown") {
        document.removeEventListener(
          rainbowSDK.webRTC.RAINBOW_ONWEBRTCCALLSTATECHANGED,
          self.onWebRTCCallChanged
        );
        if (self.exit) {
          await self.$router.push({ name: "chatbot" });
        }
      }
    },
    endCall: async function() {
      let self = this;
      self.exit = true;
      await rainbowSDK.webRTC.release(self.call);
      console.log("Session Ended");
    },
    moveToChat: async function() {
      console.log("moving to chat");
      await rainbowSDK.webRTC.release(this.call);
      await this.$router.push({ name: "chatbot" });
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
}
.callBox {
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
