
<script>
import patientService from '../services/patient-service';
import providerService from '../services/provider-service';

export default {
  data() {
    return {
      patient: {},
      provider: {}
    };
  },

  created() {
    const patientId = this.$route.params.id;
    this.getPatientById(patientId);
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
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="patient-detail-card">
      <h2 class="card-title">Patient Details</h2>
      <div class="patient-info">
        <p><strong>ID:</strong> {{ patient.id }}</p>
        <p><strong>First Name:</strong> {{ patient.firstName }}</p>
        <p><strong>Last Name:</strong> {{ patient.lastName }}</p>
        <p><strong>Date of Birth:</strong> {{ patient.dob }}</p>
        <p><strong>SSN:</strong> {{ patient.socialSecurityNumber }}</p>
        <p><strong>Gender:</strong> {{ patient.gender }}</p>
        <p><strong>Insurance Provider:</strong> {{ patient.insuranceProvider }}</p>
        <p><strong>PCP:</strong> {{ provider.firstName }} {{ provider.lastName }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.patient-detail-card {
  background-color: #333;
  color: white;
  transition: background-color 0.3s;
}
</style>