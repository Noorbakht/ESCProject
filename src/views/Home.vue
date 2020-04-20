<template>
  <v-container fluid class="pa-0">
    <div class="text-center">
      <header fixed class="header">
        <h1>Customer Support Page</h1>
        <v-card id="card" class="mx-auto" color="#000000" dark max-width="400">
          <v-card-title class="justify-center">
            <span class="title font-weight-light">
              Number of agents available for banking-related
              questions:
            </span>
          </v-card-title>
          <v-card-text class="headline font-weight-bold">
            <h1>{{ banking }}</h1>
          </v-card-text>
          <v-card-actions>
            <v-list-item class="grow">
              <v-list-item-avatar color="grey darken-3">
                <v-img
                  class="elevation-6"
                  src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                ></v-img>
              </v-list-item-avatar>
            </v-list-item>
          </v-card-actions>
        </v-card>
        <v-card id="card1" class="mx-auto" color="#000000" dark max-width="400">
          <v-card-title>
            <span class="title font-weight-light">
              Number of agents available for investment-related
              questions:
            </span>
          </v-card-title>
          <v-card-text class="headline font-weight-bold">
            <h1>{{ investment }}</h1>
          </v-card-text>
          <v-card-actions>
            <v-list-item class="grow">
              <v-list-item-avatar color="grey darken-3">
                <v-img
                  class="elevation-6"
                  src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                ></v-img>
              </v-list-item-avatar>
            </v-list-item>
          </v-card-actions>
        </v-card>
        <v-card id="card2" class="mx-auto" color="#000000" dark max-width="400">
          <v-card-title>
            <span class="title font-weight-light">Number of agents available for general enquiries:</span>
          </v-card-title>
          <v-card-text class="headline font-weight-bold">
            <h1>{{ GE }}</h1>
          </v-card-text>
          <v-card-actions>
            <v-list-item class="grow">
              <v-list-item-avatar color="grey darken-3">
                <v-img
                  class="elevation-6"
                  src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                ></v-img>
              </v-list-item-avatar>
            </v-list-item>
          </v-card-actions>
        </v-card>
      </header>
      <div class="button">
        <v-btn
          id="button"
          width="100%"
          height="180"
          x-large
          color="#B0BEC5"
          dark
          :to="{ name: 'guest' }"
        >
          <h1 class="subtitle-2 text-center">Chat with our agents now!</h1>
        </v-btn>
      </div>
    </div>
  </v-container>
</template>
<script>
import rainbowSDK from "rainbow-web-sdk";
import axios from "axios";

export default {
  name: "Home",
  data() {
    return {
      investment: "",
      banking: "",
      GE: "",
      timer: ""
    };
  },
  created() {
    document.addEventListener(rainbowSDK.RAINBOW_ONLOADED, this.onLoaded);
    rainbowSDK.start();
    rainbowSDK.load();
    this.getLiveData();
    this.timer = setInterval(this.getLiveData, 10000);
  },
  methods: {
    onLoaded: function() {
      //set app id
      var applicationID = "ecb354505ef711ea9a6dcf004cf8c14e";
      //set app secret
      var applicationSecret =
        "8qXAAAXSKPN2JirciZwpoTkkzqLX8AWx55kg55WRFSmVGrIfb2xEleTeuwp44qVB";
      rainbowSDK.setVerboseLog(false);
      rainbowSDK
        .initialize(applicationID, applicationSecret)
        .then(() => {
          console.log("[DEMO] :: Rainbow SDK is initialized!");
        })
        .catch(err => {
          console.log("[DEMO] :: Something went wrong with the SDK...", err);
        });
      console.log("onLoaded()");
    },
    getLiveData() {
      axios
        .get("https://limitless-sierra-11102.herokuapp.com/availability")
        .then(response => {
          console.log(response.data);
          console.log(response.data.totalBank);
          console.log(response.data.totalInvest);
          console.log(response.data.totalGE);
          console.log(response.data.availBank);
          console.log(response.data.availInvest);
          console.log(response.data.availGE);
          this.investment = response.data.availInvest;
          this.banking = response.data.availBank;
          this.GE = response.data.availGE;
        });
    }
  },
  beforeDestroy() {
    clearInterval(this.timer);
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* h3 {
    margin: 40px 0 0;
}
ul {
    list-style-type: none;
    padding: 0;
}
li {
    display: inline-block;
    margin: 0 10px;
    text-decoration: none;
}
a {
    color: #42b983;
} */
.header {
  background-image: url("../assets/bank.jpg");
  color: #fff;
  text-align: center;
  padding: 300px;
  overflow: hidden;
}
.header a {
  color: #fff;
  padding-right: 5px;
  text-decoration: none;
}
html,
body {
  max-height: 100%;
  padding-bottom: 0 !important;
  margin: 0;
  overflow-y: hidden;
  position: fixed;
}
#card {
  margin-top: 140px;
  z-index: 1;
  margin-right: 285px;
  right: 300px;
  padding-bottom: 0 !important;
}
#card1 {
  margin-top: 100px;
  z-index: 1;
  margin-left: 500px;
  left: 460px;
  bottom: 300px;
  padding-bottom: 0 !important;
}
#card2 {
  z-index: 1;
  bottom: 505px;
  margin: 0 auto;
  padding-bottom: 0 !important;
}
.button {
  position: relative;
  bottom: 850px;
  text-decoration: none;
  border: 0;
  padding-bottom: 0 !important;
}
</style>
