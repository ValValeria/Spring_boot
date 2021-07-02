<template>
  <div class="users w-100">
    <BasicLayout title="Our users">
      <div class="users__search center mb">
        <form class="row w-100 card-sm center">
          <div>
            <label for="inputPassword2" class="visually-hidden">Search</label>
            <input type="text" class="form-control" id="inputPassword2" placeholder="Type here ...">
          </div>
          <div>
            <ButtonComponent>
              Find
            </ButtonComponent>
          </div>
        </form>
      </div>
      <div class="users__list">
          <FlexLayout v-if="users.length">
              <div class="users__item" v-for="user in users">
                <CardComponent
                    :title="user.username"
                    :description="user.role"
                    :id="user.id"
                    :key="Math.random()"/>
              </div>
          </FlexLayout>
      </div>
    </BasicLayout>
  </div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import ButtonComponent from "../components/ButtonComponent";
import FlexLayout from "../layouts/FlexLayout";
import CardComponent from "../components/CardComponent";

export default {
  name: "UsersPageComponent",
  components: {FlexLayout, ButtonComponent, BasicLayout, CardComponent},
  data: function(){
    return {
      page: 0,
      size: 3,
      users: []
    }
  },
  async mounted(){
     const response = await fetch(`/api/users?page=${this.page}&size=${this.size}`);

     if(response.ok){
       const data = await response.json();
       this.users = data.data.pagination.content;
     }
  }
}
</script>

<style scoped lang="scss">
@import "../variables";

form{
  max-width: 700px;
}

form div:nth-child(1){
  flex-grow: 1;
  margin-right: $space;
}

.users__item{
  flex: 1 1 33%;
}
</style>