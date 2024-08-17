
<script>
import patientService from '../services/patient-service';
import providerService from '../services/provider-service';
import patientPlaceHolder from '../assets/img/patientPlaceHolder.png';

export default {
  data() {
    return {
      patient: {},
      updatedPatient: {},
      provider: {},
      contact: {},
      updatedContact: {},
      newContact: {},
      demographics: {},
      updatedDemographics: {},
      images: {
        patientPlaceHolder,
      },      
      isEditing: false,
      isAdding: false,
      isDeleting: false
    };
  },

  created() {
    const patientId = this.$route.params.id;
    this.contact.patientId = patientId;
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
        console.log(this.contact);
      });
    },

    getDemographics(id) {
      patientService.getDemographics(id).then(response => {
        this.demographics = response.data;
        console.log(this.demographics);
      });
    },
    updatePatient(id, updatedPatient) {
      if (!updatedPatient.firstName) {
        updatedPatient.firstName = this.patient.firstName;
      }
      if (!updatedPatient.lastName) {
        updatedPatient.lastName = this.patient.lastName;
      }
      if (!updatedPatient.dateOfBirth) {
        updatedPatient.dateOfBirth = this.patient.dateOfBirth;
      }
      if (!updatedPatient.gender) {
        updatedPatient.gender = this.patient.gender;
      }      
      if (!updatedPatient.insuranceProvider) {
        updatedPatient.insuranceProvider = this.patient.insuranceProvider;
      }
      if (!updatedPatient.primaryCareProviderId) {
        updatedPatient.primaryCareProviderId = this.patient.primaryCareProviderId;
      }
      if (!updatedPatient.socialSecurityNumber) {
        updatedPatient.socialSecurityNumber = this.patient.socialSecurityNumber;
      }

      patientService.updatePatient(id, updatedPatient).then(response => {
        console.log('Patient updated successfully:', response.data);
        this.getPatientById(id);
        this.isEditing = false; 
        this.updatedPatient = {}; 
      }).catch(error => {
        console.error('Error updating patient:', error);
      });
    },
    createContact(newContact) {
      patientService.createContact(newContact).then(response => {
        console.log('Contact created successfully:', response.data);
        this.getContactInfo(id);
      });
    },
    updateContact(id, updatedContact) {
      if (!updatedContact.email) {
        updatedContact.email = this.contact.email;
      }
      if (!updatedContact.phoneNumber) {
        updatedContact.phoneNumber = this.contact.phoneNumber;
      }
      if (!updatedContact.address) {
        updatedContact.address = this.contact.address;
      }
    
      patientService.updateContact(id, updatedContact).then(response => {
        console.log('Contact updated successfully:', response.data);
        this.getContactInfo(id);
        this.isEditing = false; 
        this.updatedContact = {}; 
      }).catch(error => {
        console.error('Error updating contact:', error);
      });

    },
    updateDemographics(id, updatedDemographics) {
      patientService.updateDemographics(id, updatedDemographics).then(response => {
        console.log('Demographics updated successfully:', response.data);
        this.getDemographics(id);
        this.isEditing = false; 
        this.updatedDemographics = {}; 
      }).catch(error => {
        console.error('Error updating demographics:', error);
      });
    },    
    toggleAdd() {
      if(this.isEditing || this.isDeleting) {
       this.isDeleting = false;
       this.isEditing = false;
       this.isModifying = false;
      }
      this.isAdding = !this.isAdding;
      this.isModifying = !this.isModifying;
   },      
   toggleEdit() {
     if(this.isDeleting || this.isAdding) {
       this.isDeleting = false;
       this.isAdding = false;
       this.isModifying = false;
     }
     this.isEditing = !this.isEditing;
     this.isModifying = !this.isModifying;
     this.searchTerm = '';
   },
   toggleDelete() {
     if(this.isEditing || this.isAdding || this.isModifying) {
       this.isModifying = false;
       this.isEditing = false;
       this.isAdding = false;
     }
     this.isDeleting =!this.isDeleting;
   }
  }
}
</script>

<template>
    <body>

<div class="menu-container">
  <button @click="$router.go(-1)" class="go-back" >
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-left-fill" viewBox="0 0 16 16">
      <path d="m3.86 8.753 5.482 4.796c.646.566 1.658.106 1.658-.753V3.204a1 1 0 0 0-1.659-.753l-5.48 4.796a1 1 0 0 0 0 1.506z"/>
    </svg> 
    Back
  </button>
  <button class="edit" @click="toggleEdit" :class="{'isEditing': isEditing}">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
      <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
      <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
    </svg>
    Edit
  </button>
</div>

<div class="detail-container">

  <div class="patient-detail-card" id="card">
  
    <div>
      <img :src="images.patientPlaceHolder" class="pfp" alt="Patient Profile Picture">
    </div>
    
    <div class="patient-info">
      <h2>{{ patient.firstName }} {{ patient.lastName }}</h2>
      <p><strong>DOB: </strong> {{ patient.dateOfBirth }} </p>
      <p><strong>Gender: </strong> {{ patient.gender }}</p>
      <p><strong>PCP: </strong> {{ provider.firstName }} {{ provider.lastName }}</p>
      <p><strong>Insurance Provider:</strong> {{ patient.insuranceProvider }}</p>
      <p><strong>SSN: </strong> {{ patient.socialSecurityNumber }}</p>
    </div>

    <div v-if="isEditing" class="patient-detail-edit-container" id="edit-card">
      <section class="name">
        <p><strong>Name: </strong>
        <input v-model="updatedPatient.firstName" placeholder="First Name">
        <input v-model="updatedPatient.lastName" placeholder="Last Name">
        </p>
      </section>
      <section class="specifications">
        <p><strong>DOB: </strong><input type="date" v-model="updatedPatient.dateOfBirth" placeholder="Date Of Birth"></p>
        <p><strong>Gender: </strong></p>
        <select id="gender" name="gender" v-model="updatedPatient.gender">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Non-binary">Non-binary</option>
            <option value="Genderqueer">Genderqueer</option>
            <option value="Genderfluid">Genderfluid</option>
            <option value="Agender">Agender</option>
            <option value="Other">Other</option>
        </select>        
        <p><strong>Insurance Provider: </strong><input v-model="updatedPatient.insuranceProvider" placeholder="Insurance"></p>
        <p><strong>SSN: </strong><input v-model="updatedPatient.socialSecurityNumber" placeholder="Social Security Number"></p>
      </section>
      <section>

        <button @click="updatePatient(patient.id, updatedPatient)">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-in-up" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M3.5 10a.5.5 0 0 1-.5-.5v-8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 .5.5v8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 0 0 1h2A1.5 1.5 0 0 0 14 9.5v-8A1.5 1.5 0 0 0 12.5 0h-9A1.5 1.5 0 0 0 2 1.5v8A1.5 1.5 0 0 0 3.5 11h2a.5.5 0 0 0 0-1z"/>
            <path fill-rule="evenodd" d="M7.646 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V14.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708z"/>
          </svg>
        </button>

      </section>      

    </div>

  </div>

  <div class="patient-contact-card" id="card">
    <h2 class="label">
      <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16">
        <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5m.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1z"/>
      </svg>
      Contact Information
      <button v-if="!contact.email && !contact.phoneNumber && !contact.address" @click="toggleAdd()" :class="{'isAdding': isAdding}">
  <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
    <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
  </svg>
</button>
    </h2>
    <section class="contact-info">

      <p><strong>Email:</strong> {{ contact.email }} </p>
      <p><strong>Phone:</strong> {{ contact.phoneNumber }}</p>
      <p><strong>Address:</strong> {{ contact.address }}</p>

    </section>
    

    <div v-if="isEditing" class="contact-detail-edit-container" id="edit-card">
      <section class="contact-info">
        <p><strong>Email: </strong><input v-model="updatedContact.email" placeholder="Email"></p>
        <p><strong>Phone: </strong><input v-model="updatedContact.phoneNumber" placeholder="Phone Number"></p>
        <p><strong>Address: </strong><input v-model="updatedContact.address" placeholder="Address"></p>
      </section>
      <section>
        <button @click="updateContact(this.patient.id, updatedContact)">
          <svg xmlns="http://www.w3.org/2000/svg" :class="{'isEditing': isEditing}" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-in-up" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M3.5 10a.5.5 0 0 1-.5-.5v-8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 .5.5v8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 0 0 1h2A1.5 1.5 0 0 0 14 9.5v-8A1.5 1.5 0 0 0 12.5 0h-9A1.5 1.5 0 0 0 2 1.5v8A1.5 1.5 0 0 0 3.5 11h2a.5.5 0 0 0 0-1z"/>
            <path fill-rule="evenodd" d="M7.646 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V14.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708z"/>
          </svg>
        </button>
      </section>
    </div>

    <div v-if="isAdding" class="contact-detail-edit-container" id="edit-card">
      <section class="contact-info">
        <p><strong>Email: </strong><input v-model="newContact.email" placeholder="Email"></p>
        <p><strong>Phone: </strong><input v-model="newContact.phoneNumber" placeholder="Phone Number"></p>
        <p><strong>Address: </strong><input v-model="newContact.address" placeholder="Address"></p>
      </section>
      <section>
        <button @click="createContact(patient.id, newContact)" >
          <svg xmlns="http://www.w3.org/2000/svg" :class="{'isAdding': isAdding}" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-in-up" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M3.5 10a.5.5 0 0 1-.5-.5v-8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 .5.5v8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 0 0 1h2A1.5 1.5 0 0 0 14 9.5v-8A1.5 1.5 0 0 0 12.5 0h-9A1.5 1.5 0 0 0 2 1.5v8A1.5 1.5 0 0 0 3.5 11h2a.5.5 0 0 0 0-1z"/>
            <path fill-rule="evenodd" d="M7.646 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V14.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708z"/>
          </svg>
        </button>
      </section>
    </div>

  </div>

  

  <div class="patients-demographics-card" id="card">
      <section class="label">

        <h2>
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-box-seam-fill" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M15.528 2.973a.75.75 0 0 1 .472.696v8.662a.75.75 0 0 1-.472.696l-7.25 2.9a.75.75 0 0 1-.557 0l-7.25-2.9A.75.75 0 0 1 0 12.331V3.669a.75.75 0 0 1 .471-.696L7.443.184l.01-.003.268-.108a.75.75 0 0 1 .558 0l.269.108.01.003zM10.404 2 4.25 4.461 1.846 3.5 1 3.839v.4l6.5 2.6v7.922l.5.2.5-.2V6.84l6.5-2.6v-.4l-.846-.339L8 5.961 5.596 5l6.154-2.461z"/>
          </svg>
          Demographics               
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
            <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
            <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
          </svg>
        </h2>

      </section>
      
      <section class="data">

        <p><strong>Age:</strong> {{ demographics.age }}</p>
        <p><strong>Race:</strong> {{ demographics.race }} </p>
        <p><strong>Ethnicity:</strong> {{ demographics.ethnicity }}</p>
        <p><strong>Religion:</strong> {{ demographics.religion }}</p>
        <p><strong>Marital Status:</strong> {{ demographics.maritalStatus }}</p>
        <p><strong>Preferred Language:</strong> {{ demographics.preferredLanguage }}</p>

      </section>
      
      <section class="edit-add-container">

        <section class="add-container">



        </section>

        <section class="edit-container" id="edit-card">



        </section>

      </section>
    
    
  </div>
  

</div> 



</body>

</template>

<style scoped>

body {
  margin-bottom: 80px;
  display: grid;
  grid-template-columns: 10% 1fr 1fr;
  grid-template-rows: 1fr;
}
.menu-container {
  grid-column: 1/2;
  width: 200;
  background-color: rgb(29, 29, 29);
  box-shadow: var(--shadow-4); 
  display: grid;
  grid-template-rows: 50px;
  align-items: center;
  justify-content: left;
  gap: 29px;
}
.go-back {
  grid-column: 1;
  padding: 10px;
  background-color: transparent;
  box-shadow: none;
  align-items: bottom;
}
.edit {
  grid-column: 2;
  padding: 10px;
  background-color: transparent;
  box-shadow: none;
  align-items: bottom;
}
.isEditing {
  color: orange;
}
.isAdding{
  color: green;
}
.isDeleting{
  color: red;
}
.pfp {
  width: 200px;
  height: 200px;
  border-radius: 100px;
}
.detail-container {
  height: 90%;
  grid-column: 2/4;
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr, 1fr 1fr;
  padding: 50px;
  padding-top: 25px;
  overflow: auto;
}
#card {
  padding: 30px;
  margin: 10px;
  align-items: center;
  border: solid rgb(29, 29, 29) 20px;
  box-shadow: var(--shadow-2);
  background-color: #333;
  color: white;
  border-radius: 100px;
}
#edit-card{
  height: 90%;
  padding-left: 10px;
  padding-top: 10px;
  border: solid rgb(48, 48, 48) 5px;
  background-color: #424242;
  box-shadow: var(--shadow-4);
  border-top-right-radius: 100px;
  border-bottom-left-radius: 100px;
  display: grid;
  grid-template-columns: 1fr 1fr 10px;
  grid-template-rows: 1fr;
}
.patient-contact-card p {
  text-decoration: underline;
}
.patient-detail-card {
  grid-row: 1/2;
  display: grid;
  grid-template-columns: 250px 1fr 1fr;
}
.patient-detail-edit-container {
  grid-column: 3;
  width: 600px;
    p {
      margin-top: 10px;
      margin-bottom: 0px;
    }
    input, select {
      margin-top: 10px;
    }
    option{
      color: rgb(155, 155, 155);
      background-color: #333;
    }
    .name {
      grid-column: 1/2;
      padding-right: 5px;
    }
    .specifications {
      grid-column: 2/3;
      padding-left: 5px;
      padding-right: 5px;
    }
    button {
      grid-column: 3/4;
      padding-left: 5px;
      padding-right: 5px;
      background-color: transparent;
      box-shadow: none;
      color: orange;
    }
  }
  .patient-contact-card {
    grid-row: 2/3;
    display: grid;
    grid-template-columns: 1fr 620px;
    grid-template-rows: 1fr;
    padding-right: 50px;
      button {
        grid-column: 2/3;
        padding-left: 5px;
        padding-right: 5px;
        background-color: transparent;
        box-shadow: none;
      }
      .label {
        grid-column: 1/2;
      }
      .contact-info {
        grid-column: 1/2;
        display: flex;
        flex-direction: column;
      }
      .contact-detail-edit-container {
        grid-column: 2/3;
        grid-row: 1/3;
        display: grid;
        grid-template-columns: 1fr 10px;
        width: 600px;
          .contact-info{
            grid-column: 1/2;
            padding-left:30px;
            padding-right:30px;
          }
      }
  }
  .patients-demographics-card {
    grid-row: 3/4;
    display: grid;
    grid-template-columns: 1fr 1fr;
      .label {
        grid-row: 1;
        grid-column: 1/3;
      }
  }
</style>