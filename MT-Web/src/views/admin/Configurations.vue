<template>
    <div class="responsive-body">
        <div class="container-view pt-5 pb-5">
            <h2>Configurações da tela principal</h2>
        </div>
        <hr>
        <div class="container-thread pt-5 pb-5">
            <h2>Configurações das threads</h2>
        </div>
    </div>
</template>

<script>

export default {
    name: 'Configurations'
}

</script>

<style scoped>
.responsive-body {
    height: 100%;
}

.container-thread{
    background-color: aqua;
}

.container-view{
    background-color: aquamarine;
}
</style>