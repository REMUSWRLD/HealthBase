
<script>
import patientService from '../services/patient-service';
import providerService from '../services/provider-service';

export default {
  data() {
    return {
      patient: {},
      provider: {},
      contact: {},
      demographics: {}
    };
  },

  created() {
    const patientId = this.$route.params.id;
    this.getPatientById(patientId);
    this.getContactInfo(patientId);
    this.getDemographics(patientId);
  },
  methods: {
    getPatientById(id) {
      patientService.getPatientById(id).then(response => {
        this.patient = response.data;
        this.getProviderById(this.patient.primaryCareProviderId)
        console.log(this.patient);
      });
    },
    getProviderById(id) {
      providerService.getProviderById(id).then(response => {
        this.provider = response.data;
        console.log(this.provider);
      });
    },

    getContactInfo(id) {
      patientService.getContact(id).then(response => {
        this.contact = response.data;
        console.log(this.patient.contact);
      });
    },

    getDemographics(id) {
          patientService.getDemographics(id).then(response => {
            this.demographics = response.data;
            console.log(this.patient.demographics);
          });
        }
  }
}
</script>

<template>
  <body>

    <div class="patient-detail-card" id="card">
      <h2 class="card-title">Patient Details</h2>
      <div class="patient-info">
        <p><strong>ID:</strong> {{ patient.id }}</p>
        <p><strong>First Name:</strong> {{ patient.firstName }}</p>
        <p><strong>Last Name:</strong> {{ patient.lastName }}</p>
        <p><strong>Date of Birth:</strong> {{ patient.dateOfBirth }}</p>
        <p><strong>SSN:</strong> {{ patient.socialSecurityNumber }}</p>
        <p><strong>Gender:</strong> {{ patient.gender }}</p>
        <p><strong>Insurance Provider:</strong> {{ patient.insuranceProvider }}</p>
        <p><strong>PCP:</strong> {{ provider.firstName }} {{ provider.lastName }}</p>
      </div>
    </div>

    <div class="patient-contact-card" id="card">
      <h2 class="card-title">Contact Information</h2>
      <div class="patient-contact-info">
        <p><strong>Email:</strong> {{ contact.email }}</p>
        <p><strong>Phone Number:</strong> {{ contact.phoneNumber }}</p>
        <p><strong>Address:</strong> {{ contact.address }}</p>
      </div>
    </div>
    
    <div class="patient-demographic-card" id="card">
      <h2 class="card-title">Demographics</h2>
      <div class="patient-demographic-info">
        <p><strong>Race:</strong> {{ demographics.race }}</p>
        <p><strong>Ethnicity:</strong> {{ demographics.ethnicity }}</p>
        <p><strong>Height:</strong> {{ demographics.height }}</p>
        <p><strong>Weight:</strong> {{ demographics.weight }}</p>
      </div>
    </div>

  </body>



    
</template>

<style scoped>

body {
  display: grid;
  grid-template-columns: 10% 1fr 10%;
  grid-template-rows: 1fr 1fr 1fr;
  overflow-y: scroll;
}

#card {
  padding: 30px;
  margin: 10px;
  border: solid black 20px;
  border-radius: 50px;
  background-color: #333;
  color: white;
}
.patient-detail-card {
  grid-column: 2/3;
}
.patient-contact-card {
  grid-column: 2/3;
  grid-row: 2/3;
}
.patient-demographic-card {
  grid-column: 2/3;
  grid-row: 3/4;
}
</style>