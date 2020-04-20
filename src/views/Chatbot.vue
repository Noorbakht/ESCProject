<template>
  <div class="chat">
    <transition name="fade">
      <Loading v-bind:isConnecting="isConnecting" v-if="!start" />
    </transition>
    <div class="chatBox" id="chatBox" ref="chatBox">
      <header class="header">
        <span class="icon">
          <v-btn :to="{ name: 'feedback' }" x-large name="toHomebutton">
            <i id="fontHouse" class="fas fa-house-user" name="toHomebutton" fa-4x>
              <br />
              <a>Home</a>
            </i>
          </v-btn>
        </span>
        <h2>Chatting</h2>
      </header>
      <v-card flat class="chat-box-list-container" ref="chatbox" fluid>
        <ul class="chat-box-list" style="word-break: keep-all">
          <li v-for="message in messages" :key="message.text" v-bind:class="message.author">
            <p>
              <v-container>
                <span>{{ message.text }}</span>
              </v-container>
            </p>
            <v-card-subtitle class="white--text text-right pr-2 pb-1">
              {{ message.author }}
              {{ message.time }}
            </v-card-subtitle>
          </li>
        </ul>
      </v-card>
    </div>
    <v-footer id="footer" fixed width="100%">
      <v-textarea
        auto-grow
        name="msgbox"
        rows="2"
        placeholder="Start typing..."
        filled
        type="text"
        v-model="txt"
      />
      <v-btn name="msgbutton" right height="60px" @click="sendMessage">Send</v-btn>
      <i name="toCall" class="fas fa-phone-square fa-3x" @click="alert"></i>
    </v-footer>
  </div>
</template>

<script>
import rainbowSDK from "rainbow-web-sdk";
import moment from "moment";
import axios from "axios";
import Loading from "./Loading";
import $ from "jquery";
import swal from "sweetalert";

export default {
  name: "Chatbot",
  props: ["firstname", "lastname"],
  components: { Loading },
  data: () => ({
    agentId: "",
    guestId: "",
    file: "",
    flag: false,
    message: "",
    icons: {
      iconfont: "md"
    },
    txt: "",
    skill: "",
    isConnecting: false,
    start: false,
    selectedFile: "",
    messages: [
      {
        text:
          "Type #support for help or type #availability to see the number of agents currently available.",
        author: "Bank",
        time: moment().format("h:mm a"),
        image: ""
      }
    ],
    conversation: ""
  }),
  created() {
    console.log(this.$route.params);
    this.gettingConnection();
  },
  methods: {
    //changed all the this to self including this.conversation
    gettingConnection: async function() {
      let self = this;
      try {
        let response = await axios.get(
          `https://limitless-sierra-11102.herokuapp.com/create_guest?firstName=${this.$route.params.firstname}&lastName=${this.$route.params.lastname}`
        );
        self.guestId = response.data.id;
        self.email = response.data.loginEmail;
        self.password = response.data.password;
        console.log("this is guest id:" + this.guestId);
        console.log(this.email);
        console.log(this.password);
        if (this.guestId) {
          this.gettingConvo();
        } else {
          console.log("can't find id");
        }
      } catch (err) {
        console.log(err);
      }
    },
    gettingConvo: async function() {
      let self = this;
      try {
        await rainbowSDK.connection.signin(this.email, this.password);
        console.log("signed in");
        //bot contact
        let contact = await rainbowSDK.contacts.searchById(
          "5e3298a2e9f12730636949d2"
        );
        console.log(contact);
        //open conversation for bot
        self.conversation = await rainbowSDK.conversations.openConversationForContact(
          contact
        );
        console.log(self.conversation);
        console.log(self.conversation.messages);
        //detecting messages from bot
        await rainbowSDK.im.getMessagesFromConversation(self.conversation);
        document.addEventListener(
          rainbowSDK.im.RAINBOW_ONNEWIMMESSAGERECEIVED,
          self.receive
        );
        self.start = true;
        document.addEventListener(
          rainbowSDK.im.RAINBOW_ONNEWIMRECEIPTRECEIVED,
          self.receipt
        );
        console.log(this.guestId);
        //get token id from agent backend
        while (this.flag == false) {
          try {
            let response = await axios.get(
              `https://limitless-sierra-11102.herokuapp.com/establish_connection?id=${this.guestId}`
            );
            if (response.data.connection == true) {
              this.flag = true;
              this.agentComeIn();
            }
          } catch (err) {
            console.log(err);
          }
        }
      } catch (err) {
        console.log(err);
      }
    },
    endChat: async function() {
      let self = this;
      this.$store.state.agentId = "";
      console.log(this.$store.state.agentId);
      await rainbowSDK.conversations.closeConversation(self.conversation);
      console.log("closing");
    },
    agentComeIn: async function() {
      let self = this;
      // document.removeEventListener(
      //   rainbowSDK.im.RAINBOW_ONNEWIMRECEIPTRECEIVED,
      //   self.receipt
      // );
      let response = await axios.get(
        `https://limitless-sierra-11102.herokuapp.com/establish_connection?id=${this.guestId}`
      );
      console.log(response);
      console.log(response.data.agentId);
      console.log(response.data.connection);
      console.log(response.data.skill);
      this.$store.state.agentId = response.data.agentId;
      console.log(this.$store.state.agentId);
      this.skill = response.data.skill;
      console.log(response);
      self.agentId = response.data.agentId;
      console.log(self.agentId);
      let contact = await rainbowSDK.contacts.searchById(self.agentId);
      console.log(contact);
      self.conversation = await rainbowSDK.conversations.openConversationForContact(
        contact
      );
      console.log(self.conversation);
      console.log(self.conversation.messages);
      await rainbowSDK.im.sendMessageToConversation(
        this.conversation,
        `Hello, I am ${this.$route.params.firstname} and I need help in ${this.skill} `
      );
      document.removeEventListener(
        rainbowSDK.im.RAINBOW_ONNEWIMMESSAGERECEIVED,
        self.receive
      );
      await rainbowSDK.im.getMessagesFromConversation(this.conversation);
      document.addEventListener(
        rainbowSDK.im.RAINBOW_ONNEWIMMESSAGERECEIVED,
        self.receive1
      );
      document.addEventListener(
        rainbowSDK.im.RAINBOW_ONNEWIMRECEIPTRECEIVED,
        self.receipt
      );
    },
    alert() {
      swal("Please only click this once you have been connected to an agent.", {
        buttons: {
          cancel: "Cancel",
          audio: {
            test: "audio",
            value: "audio"
          },
          video: true
        }
      }).then(value => {
        switch (value) {
          case "video":
            this.callingAgentVideo();
            break;
          case "audio":
            this.callingAgentAudio();
            break;
          default:
            break;
        }
      });
    },
    callingAgentVideo: async function() {
      console.log(this.$store.state.agentId);
      this.$router.push({
        name: "call"
      });
    },
    callingAgentAudio: async function() {
      console.log(this.$store.state.agentId);
      this.$router.push({
        name: "callAudio"
      });
    },
    //allows user to send message onto chatbot
    sendMessage() {
      let self = this;
      if (self.txt != "") {
        let message = self.txt;
        rainbowSDK.im.sendMessageToConversation(self.conversation, message);
        self.messages.push({
          text: message,
          author: "client",
          time: moment().format("h:mm a")
        });
        $("html, body").animate({ scrollTop: $(document).height() }, "slow");
        self.txt = "";
      }
    },
    //when message received
    receive: function(event) {
      let self = this;
      rainbowSDK.im.markMessageFromConversationAsRead(
        event.detail.conversation,
        event.detail.message
      );
      console.log(event.detail.message.data);
      console.log(event.detail.message.side);
      self.messages.push({
        text: event.detail.message.data,
        author: "Bank",
        time: moment().format("h:mm a")
      });
      $("html, body").animate({ scrollTop: $(document).height() }, "slow");
    },
    receive1: function(event) {
      let self = this;
      console.log(event.detail.message.data);
      console.log(event.detail.message.side);
      self.messages.push({
        text: event.detail.message.data,
        author: "Agent",
        time: moment().format("h:mm a")
      });
      $("html, body").animate({ scrollTop: $(document).height() }, "slow");
    },
    //when you send out message
    receipt: function(event) {
      console.log("receipt");
      console.log(event.detail.message.data);
      console.log(event.detail.message.side);
    }
  },
  mounted() {
    const self = this;
    window.addEventListener("keyup", function(event) {
      if (event.keyCode == 13) {
        self.sendMessage();
      }
    });
  }
};
</script>

<style scoped lang="scss">
.chat-box-list {
  display: flex;
  flex-direction: column;
  list-style-type: none;
  padding-bottom: 90px;
}
.chat-box-list-container {
  overflow-y: scroll;
  scrollbar-width: auto;
  margin-bottom: 1px;
  overflow-x: hidden;
}
.chat-box-list {
  padding-left: 10px;
  padding-right: 10px;
  span {
    padding: 8px;
    color: white;
    border-radius: 4px;
  }
  .Bank {
    span {
      background: #937cca;
      padding: 16px;
      display: grid;
    }
    p {
      float: right;
    }
  }
  .client {
    span {
      background: #48d382;
      padding: 16px;
      display: grid;
    }
    // p {
    //   float: left;
    // }
  }
  .Agent {
    span {
      background: salmon;
      padding: 16px;
      display: grid;
    }
    // p {
    //   float: left;
    // }
  }
}
.Bank {
  margin-right: auto !important;
}
.client {
  margin-left: auto !important;
}
.Agent {
  margin-right: auto !important;
}
.header {
  background: rgb(2, 106, 167);
  color: #fff;
  text-align: center;
  padding: 20px;
}
.footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 50px;
  text-align: center;
  background-color: #ccc;
}
.icon {
  margin-right: 6000px;
  font-size: 80px;
  height: 20px;
  vertical-align: middle;
  padding: 40px 45px 40px 35px;
  line-height: 5px !important;
  top: 30px;
  position: relative;
}
.icon2 {
  margin-left: 1500px;
  font-size: 80px;
  height: 20px;
  vertical-align: middle;
  padding: 40px 45px 40px 35px;
  line-height: 5px !important;
  top: 30px;
  position: relative;
}
#popup {
  position: absolute;
  width: 300px;
  height: 700px;
  z-index: 15;
  top: 50%;
}
.submit {
  position: fixed;
  bottom: 20px;
  width: 70px;
  height: 20px; /*height: auto;*/
  margin-left: 750px;
  border: 0px solid #d6d6d6;
  z-index: 99;
  padding: 0;
  size: 500px;
  text-align: center;
}
.rating-text {
  font-weight: bold;
  font-size: 1.9em;
  border: 1px solid #cfcfcf;
  padding-left: 10px;
  padding-right: 10px;
  border-radius: 5px;
  color: #999;
}
.stars {
  margin-bottom: 90px;
  margin-top: 50px;
}
router-link {
  text-decoration: none;
}
</style>
