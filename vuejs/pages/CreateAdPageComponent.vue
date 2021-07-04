<template>
  <div class="w-100">
    <BasicLayout :isSection="false">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white">
          <li class="breadcrumb-item active" aria-current="page">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item" aria-current="page">
            <router-link :to="this.$router.history.current.path">Create a new ad</router-link>
          </li>
        </ol>
      </nav>
    </BasicLayout>
    <BasicLayout :is-section="false" title="Create a new ad">
      <div class="create-ad w-100 center ">
        <form method="post" enctype="multipart/form-data" class="card-sm mb" ref="form">
          <div class="form-group">
            <label class="form-control-label">Title</label>
            <input type="text" name="title" class="form-control">
          </div>
          <div class="form-group">
            <div class="mb-3">
              <label for="formFile" class="form-control-label">Image</label>
              <input class="form-control" type="file" id="formFile" name="photo" accept="image/*">
            </div>
          </div>

          <div class="form-group">
            <label class="form-control-label">Description</label>
            <textarea name="description" class="form-control" ></textarea>
          </div>

          <div class="w-100  loginbttm">
            <div class="w-100 login-btm login-text">
             <ul class="list-group">
               <li class="list-group-item list-group-item-primary" v-if="isAdded">
                  Your advertisement is successfully added
               </li>
               <li class="list-group-item list-group-item-danger" v-if="isError">
                  Please, check the validity of fields
               </li>
             </ul>
            </div>
            <div class=" login-btm login-button w-100 d-flex justify-content-center">
              <button type="submit" class="btn btn-outline-primary" @click.prevent="submit">Submit</button>
            </div>
          </div>
        </form>
      </div>
    </BasicLayout>
  </div>
</template>

<script>

import BasicLayout from "../layouts/BasicLayout";
export default {
  name: "CreateAdPageComponent",
  components: {BasicLayout},
  data: function(){
    return {
      isAdded: false,
      isError: false
    }
  },
  mounted(){
    if(!this.$store.state.isAuth){
       this.$router.push("/");
    }
  },
  methods:{
    async submit(){
       const formData = new FormData(this.$refs.form);
       const response = await fetch('/create-ad', {
           method: 'POST',
           body: formData
       });

       if(response.ok){
          const result = await response.json();

          if(result.status === "ok"){
            this.isAdded = true;
          } else {
            this.isError = true;
          }
       }
    }
  }
}
</script>

<style scoped>
form{
  min-width: 500px;
  max-width: 800px;
}
</style>