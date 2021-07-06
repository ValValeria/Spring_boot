<template>
  <section id="hero" class="d-flex align-items-center position-relative">
    <div class="hero-img">
      <img alt="" src="/static/hero-bg.jpg"/>
    </div>

    <div class="container position-relative aos-init aos-animate" data-aos="fade-up" data-aos-delay="100">
      <div class="row justify-content-center">
        <div class="col-xl-7 col-lg-9 text-center">
          <h1>
            {{"Create your own advertisement".toUpperCase()}}
          </h1>
        </div>
      </div>
      <div class="text-center">
        <a href="#about" class="btn-get-started scrollto">Get Started</a>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "BannerComponent"
}
</script>

<style scoped lang="scss">
.hero-img{
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  width: 100%;
  height: 100%;
  filter: blur(8px);

  img{
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}
</style>