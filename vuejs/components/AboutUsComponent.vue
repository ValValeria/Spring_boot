<template>
  <section id="about" class="about">
    <BasicLayout :title="'About us'.toUpperCase()">
      <div class="row content">
        <div class="col-lg-6">
          <p>
            InAds helps improve your ads over time. There are three steps:
          </p>
          <ul>
            <li><i class="ri-check-double-line"></i>
              Create your own account
            </li>
            <li><i class="ri-check-double-line"></i>
              Create your ads
            </li>
            <li><i class="ri-check-double-line"></i>
              Get more calls to your business
            </li>
          </ul>
        </div>
        <div class="col-lg-6 pt-4 pt-lg-0">
          <p>
            We know you want to focus on what’s most important – running your business. So our smart technology will help find ways to improve your ads and get you better results.
          </p>
          <router-link to="/ads" class="btn-learn-more">Learn More</router-link>
        </div>
      </div>
    </BasicLayout>
  </section>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
export default {
  name: "AboutUsComponent",
  components: {BasicLayout}
}
</script>

<style scoped>

</style>