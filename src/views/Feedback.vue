<template>
    <div id="app">
        <header class="header">
            <h2 class="transparent">Feedback</h2>
            <star-rating
                class="star"
                v-bind:increment="0.5"
                v-bind:max-rating="5"
                inactive-color="#ffffff"
                active-color="#FFFF00"
                v-bind:star-size="80"
            ></star-rating>
            <div class="square">
                <!-- <VueEmoji class="emoji" @input="onInput" :value="myText" /> -->
            </div>
        </header>
    </div>
</template>

<script>
import StarRating from "vue-star-rating";
// import VueEmoji from "emoji-vue";

export default {
    name: "Feedback",
    components: {
        // VueEmoji,
        StarRating
    },
    data: () => {
        return {
            rating: 0,
            ratingSelected: false,
            dialog: null
        };
    },
    methods: {
        changeRating: function(val) {
            // If rating is already selected,
            // you can't change it anymore.
            if (this.rating === 0) {
                this.rating = val;
                this.ratingSelected = true;
            }
        },
        onInput: function(event) {
            //event.data contains the value of the textarea
            console.log(event);
        },
        openDialog() {
            if (this.dialog) {
                this.dialog.show();
            }
        },

        assignDialogRef(dialog) {
            this.dialog = dialog;
        }
    }
};
</script>

<style scoped>
.header {
    background-image: url("../assets/background.jpeg");
    color: #fff;
    text-align: center;
    padding: 300px;
    height: 770px;
    width: 100%;
}
.transparent {
    background-color: cornflowerblue !important;
    opacity: 0.8;
    border-color: transparent !important;
    height: 200px;
    top: -280px;
    bottom: 100px;
    height: 20px;
    vertical-align: middle;
    padding: 40px 45px 40px 35px;
    line-height: 5px !important;
    position: relative;
    margin: auto;
    width: 40%;
    border-radius: 25px;
    color: black;
}
.star {
    top: -200px;
    position: relative;
    margin: auto;
    vertical-align: middle;
    left: 350px;
}
.emoji {
    top: -100px;
    position: relative;
    margin: auto;
    vertical-align: middle;
    left: 350px;
}
.square {
    margin: auto;
    position: relative;
    width: 60%;
    overflow: hidden;
    border-radius: 10px;
    color: darksalmon;
}
</style>
