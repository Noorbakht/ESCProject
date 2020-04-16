<template>
  <v-app id="inspire">
    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>Sign Up form</v-toolbar-title>
                <v-spacer />
                <v-tooltip bottom>
                  <span>Source</span>
                </v-tooltip>
                <v-tooltip right>
                  <span>Codepen</span>
                </v-tooltip>
              </v-toolbar>
              <v-card-text>
                <v-form @submit.prevent="signup">
                  <v-text-field
                    label="Email"
                    name="email"
                    type="email"
                    v-model="email"
                  />
                  <v-text-field
                    id="password"
                    label="Password"
                    name="password1"
                    type="password"
                    v-model="password1"
                  />
                  <p v-if="errorText" class="text-danger">{{ errorText }}</p>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn color="primary" :to="{ name: 'Home' }">Back</v-btn>
                <v-btn type="submit" v-on:click="signup" color="primary"
                  >Sign Up</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: "signup",
  data() {
    return {
      email: "",
      password1: "",
      errorText: null
    };
  },
  methods: {
    signup() {
      if (this.email && this.password1) {
        this.$router.push({
          name: "chatbot",
          params: { email: this.email, password1: this.password1 }
        });
      } else {
        this.errorText = "Please fill in the necessary details!";
      }
    }
  }
};
</script>
