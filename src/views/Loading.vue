<template>
  <div class="load">
    <h1
      v-if="!isConnecting"
      class="font-weight-light ma-10"
    >Bringing you to your page in a short while</h1>
    <v-progress-circular
      :rotate="-90"
      :size="200"
      :width="15"
      :value="value"
      color="teal"
      indeterminate
    >{{ value }}</v-progress-circular>
    <v-btn
      depressed
      x-large
      id="cancel"
      v-if="!isConnecting"
      class="ma-10 green white--text"
      :to="{ name: 'Home' }"
    >
      <h2 class="font-weight-medium">Cancel</h2>
    </v-btn>
  </div>
</template>

<script>
export default {
  name: "LoadingPage",
  props: {
    isConnecting: Boolean
  },
  data() {
    return {
      interval: {},
      value: 0
    };
  },
  beforeDestroy() {
    clearInterval(this.interval);
  },
  mounted() {
    this.interval = setInterval(() => {
      if (this.value === 100) {
        return (this.value = 100);
      }
      this.value += 10;
    }, 1000);
  }
};
</script>

<style scoped>
.v-progress-circular {
  margin: 1rem;
}
.load {
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100vw;
  z-index: 100;
  background-color: white;
}
</style>
