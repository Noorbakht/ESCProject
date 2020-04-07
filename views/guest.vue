<template>
  <v-app id="inspire">
    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>Guest Account form</v-toolbar-title>
                <v-spacer />
                <v-tooltip bottom>
                  <span>Source</span>
                </v-tooltip>
                <v-tooltip right>
                  <span>Codepen</span>
                </v-tooltip>
              </v-toolbar>
              <v-card-text>
                <v-form @submit.prevent="guest">
                  <v-text-field
                    label="First Name"
                    name="firstname"
                    type="text"
                    v-model="firstname"
                  />
                  <v-text-field label="Last Name" name="lastname" type="text" v-model="lastname" />
                  <p v-if="errorText" class="text-danger">{{ errorText }}</p>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn color="primary" :to="{ name: 'Home' }">Back</v-btn>
                <v-btn type="submit" v-on:click="guest" color="primary">Login</v-btn>
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
  name: "guest",
  data() {
    return {
      firstname: "",
      lastname: "",
      errorText: null
    };
  },
  methods: {
    guest() {
      if (this.firstname && this.lastname) {
        this.$router.push({
          name: "chatbot",
          params: { firstname: this.firstname, lastname: this.lastname }
        });
      } else {
        this.errorText = "Please fill in the necessary details!";
      }
    }
  }
};
</script>
