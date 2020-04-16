<template>
  <v-app id="inspire">
    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer />
                <v-tooltip bottom>
                  <span>Source</span>
                </v-tooltip>
                <v-tooltip right>
                  <span>Codepen</span>
                </v-tooltip>
              </v-toolbar>
              <v-card-text>
                <v-form @submit.prevent="login">
                  <v-text-field placeholder="Email" name="name" type="email" v-model="name" />

                  <v-text-field
                    id="password"
                    placeholder="Password"
                    name="password"
                    type="password"
                    v-model="password"
                  />
                  <p v-if="errorText" class="text-danger">{{ errorText }}</p>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn color="primary" :to="{ name: 'Home' }">Back</v-btn>
                <v-btn name="tochatbotbtn" type="submit" v-on:click="login" color="primary">Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
// import $ from "jquery";

export default {
  name: "login",
  data() {
    return {
      name: "",
      password: "",
      errorText: null
    };
  },
  methods: {
    login() {
      if (this.name && this.password) {
        this.$router.push({
          name: "chatbot",
          params: { name: this.name, password: this.password }
        });
      } else {
        this.errorText = "Please fill in the necessary details!";
      }
    }
  }
};
</script>
