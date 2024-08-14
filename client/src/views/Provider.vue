<script>
import providerService from '../services/provider-service';

export default {
  data() {
    return {
      provider: {},
      contactInfo: {}
    };
  },

  created() {
    const providerId = this.$route.params.id;
    this.getProviderById(providerId);
    this.getContactInfo(providerId);
  },
  methods: {
    getProviderById(id) {
      providerService.getProviderById(id).then(response => {
        this.provider = response.data;
        console.log(this.provider);
      }).catch(error => {
        console.error('Error fetching provider:', error);
      });
    },
    getContactInfo(id) {
      providerService.getContact(id).then(response => {
        this.contactInfo = response.data;
        console.log(this.contactInfo);
      }).catch(error => {
        console.error('Error fetching contact info:', error);
      });
    }
  }
}
</script>

<template> 
  <body>
    
    <div class="provider-detail-card" id="card">
      <h2>Provider Details</h2>
      <div class="provider-info">
        <p><strong>ID:</strong> {{ provider.id }}</p>
        <p><strong>First Name:</strong> {{ provider.firstName }}</p>
        <p><strong>Last Name:</strong> {{ provider.lastName }}</p>
        <p><strong>Specialty:</strong>Coming soon...</p>
      </div>
    </div>

    <div class="provider-contact-card" id="card">
      <h2>Contact Information</h2>
      <p><strong>Email:</strong> {{ contactInfo.email }} </p>
      <p><strong>Phone:</strong> {{ contactInfo.phoneNumber }}</p>
    </div>

  </body>








  
</template>

<style scoped>

body {
  display: grid;
  grid-template-columns: 10% 1fr 10%;
  grid-template-rows: 1fr 1fr 1fr;
}
#card {
  padding: 30px;
  margin: 10px;
  border: solid black 20px;
  border-radius: 50px;
  background-color: #333;
  color: white;
}
.provider-detail-card {
  grid-column: 2/3;
}
.provider-contact-card {
  grid-column: 2/3;
  grid-row: 2/3;
}
</style>