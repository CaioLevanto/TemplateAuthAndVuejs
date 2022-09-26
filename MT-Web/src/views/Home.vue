<template>
    <div class="responsive-body">
        <div class="pt-5 pb-5">
            <FormTravel />
        </div>
        <hr>
        <div>
            <b-container>

            </b-container>
        </div>
    </div>
</template>

<script>
import UserService from '../services/user.service';
import EventBus from "@/common/EventBus";
import FormTravel from '@/components/forms/FormTravel.vue';

export default {
    name: "Home",
    data() {
        return {
            content: ""
        };
    },
    mounted() {
        UserService.getPublicContent().then(response => {
            this.content = response.data;
        }, error => {
            this.content =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
            if (error.response && error.response.status === 403) {
                EventBus.dispatch("logout");
            }
        });
    },
    components: { FormTravel }
};
</script>

<style scoped>
    
.responsive-body {
    height: 100%;
}
</style>