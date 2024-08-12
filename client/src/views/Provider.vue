<script>
import providerService from '../services/provider-service';

export default {
  data() {
    return {
      provider: {}
    };
  },

  created() {
    const providerId = this.$route.params.id;
    this.getProviderById(providerId);
  },
  methods: {
    getProviderById(id) {
      providerService.getProviderById(id).then(response => {
        this.provider = response.data;
        console.log(this.provider);
      }).catch(error => {
        console.error('Error fetching provider:', error);
      });
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="provider-detail-card">
      <h2 class="card-title">Provider Details</h2>
      <div class="provider-info">
        <p><strong>ID:</strong> {{ provider.id }}</p>
        <p><strong>First Name:</strong> {{ provider.firstName }}</p>
        <p><strong>Last Name:</strong> {{ provider.lastName }}</p>
        <p><strong>Specialty:</strong>Coming soon...</p>
        <p><strong>Email:</strong>Coming soon...</p>
        <p><strong>Phone:</strong>Coming soon...</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.provider-detail-card {
  background-color: #333;
  color: white;
}
</style>