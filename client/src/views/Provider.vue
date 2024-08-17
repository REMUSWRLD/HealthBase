<script>
import providerService from '../services/provider-service';
import patientService from '../services/patient-service';
import providerPlaceHolder from '../assets/img/providerPlaceHolder.png';
import { BIconImages } from 'bootstrap-icons-vue';

export default {
  data() {
    return {
      provider: {},
      updatedProvider: {},
      patients: [],
      contactInfo: {},
      images: {
        providerPlaceHolder,
      },
      isEditing: false
    };
  },

  created() {
    const providerId = this.$route.params.id;
    this.getProviderById(providerId);
    this.getPatientsByPCP(providerId);
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
    getPatientsByPCP(id) {
      patientService.getPatientsByPCP(id).then(response => {
        this.patients = response.data;
          console.log(this.patients);
        }).catch(error => {
          console.error('Error fetching patients:', error);
        });
    },
    getContactInfo(id) {
      providerService.getContact(id).then(response => {
        this.contactInfo = response.data;
        console.log(this.contactInfo);
      }).catch(error => {
        console.error('Error fetching contact info:', error);
      });
    },
    updateProvider(id, updatedProvider) {
      if (!updatedProvider.firstName) {
        updatedProvider.firstName = this.provider.firstName;
      }
      if (!updatedProvider.lastName) {
        updatedProvider.lastName = this.provider.lastName;
      }
      if (!updatedProvider.credentials) {
        updatedProvider.credentials = this.provider.credentials;
      }
      if (!updatedProvider.specialty) {
        updatedProvider.specialty = this.provider.specialty;
      }
      if (updatedProvider.acceptingNewPatients === null || updatedProvider.acceptingNewPatients === undefined) {
        updatedProvider.acceptingNewPatients = this.provider.acceptingNewPatients;
      }
      if (!updatedProvider.languagesSpoken) {
        updatedProvider.languagesSpoken = this.provider.languagesSpoken;
      }

      providerService.updateProvider(id, updatedProvider).then(response => {
        console.log('Provider updated successfully:', response.data);
        this.getProviderById(id);
        this.isEditing = false; 
        this.updatedProvider = {}; 
      }).catch(error => {
        console.error('Error updating provider:', error);
      });
    },
    toggleEdit() {
      if(this.isEditing) {
        this.isEditing = false;
      } else {
        this.isEditing = true;
      }
    }
  }
}
</script>

<template> 
  <body>

    <div class="menu-container">
      <button @click="$router.go(-1)" class="go-back">
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

      <div class="provider-detail-card" id="card">
      
        <div>
          <img :src="images.providerPlaceHolder" class="pfp" alt="Provider Profile Picture">
        </div>
        
        <div class="provider-info">
          <h2>Dr. {{ provider.firstName }} {{ provider.lastName }}</h2>
          <p><strong>Credential: </strong> {{ provider.credentials }} </p>
          <p><strong>Specialty: </strong> {{ provider.specialty }}</p>
          <p><strong>Case Load: </strong> {{ patients.length }}</p>
          <p><strong>Accepting New Patients:</strong> {{ provider.acceptingNewPatients ? 'Yes' : 'No'}}</p>
          <p><strong>Languages Spoken: </strong> {{ provider.languagesSpoken }}</p>
        </div>

        <div v-if="isEditing" class="provider-detail-edit-container">
          <section class="name">
            <p><strong>Name: </strong>
            <input v-model="updatedProvider.firstName" placeholder="First Name">
            <input v-model="updatedProvider.lastName" placeholder="Last Name">
            </p>
          </section>
          <section class="specifications">

            <p><strong>Credentials: </strong><input v-model="updatedProvider.credentials" placeholder="Credentials"></p>
            <p><strong>Specialty: </strong><input v-model="updatedProvider.specialty" placeholder="Specialty"></p>
            <p><strong>Accepting New Patients: </strong>
              <select v-model="updatedProvider.acceptingNewPatients">
                <option :value="true">Yes</option>
                <option :value="false">No</option>
              </select>
            </p>
            <p><strong>Languages Spoken: </strong><input v-model="updatedProvider.languagesSpoken" placeholder="Languages Spoken"></p>
          </section>
          <section>

            <button @click="updateProvider(provider.id, updatedProvider)">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-in-up" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M3.5 10a.5.5 0 0 1-.5-.5v-8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 .5.5v8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 0 0 1h2A1.5 1.5 0 0 0 14 9.5v-8A1.5 1.5 0 0 0 12.5 0h-9A1.5 1.5 0 0 0 2 1.5v8A1.5 1.5 0 0 0 3.5 11h2a.5.5 0 0 0 0-1z"/>
                <path fill-rule="evenodd" d="M7.646 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V14.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708z"/>
              </svg>
            </button>

          </section>      
    
        </div>

      </div>

      <div class="provider-contact-card" id="card">
        <h2>
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16">
            <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5m.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1z"/>
          </svg>
          Contact Information
        </h2>
        <p><strong>Email:</strong> {{ contactInfo.email }} </p>
        <p><strong>Phone:</strong> {{ contactInfo.phoneNumber }}</p>
        <p><strong>Address:</strong> {{ contactInfo.address }}</p>
      </div>

      <div class="provider-patients-card" id="card">
        <h2>Case Load</h2>
        <div v-if="patients.length > 0">
          <ul>
            <li v-for="patient in patients" :key="patient.patientId">
              {{ patient.firstName }} {{ patient.lastName }}
            </li>
          </ul>
          
        </div>
        <div v-else>
          <p>No patients currently assigned to this provider.</p>
        </div>
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
  grid-template-rows: 1fr, 1fr;
  padding: 50px;
  padding-top: 25px;
  overflow: none;
}
#card {
  padding: 30px;
  margin: 10px;
  align-items: center;
  border: solid rgb(29, 29, 29) 20px;
  box-shadow: var(--shadow-2);
  background-color: #333;
  color: white;
}
.provider-contact-card p {
  text-decoration: underline;
}
.provider-detail-card {
  grid-row: 1/2;
  display: grid;
  grid-template-columns: 250px 1fr 1fr;
  border-top-left-radius: 100px;
}
.provider-detail-edit-container {
  grid-column: 3;
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
.provider-contact-card {
  grid-row: 2/3;
  display: grid;
  grid-template-columns: 1fr;
  border-bottom-left-radius: 100px;
}
.provider-patients-card {
  grid-column: 2;
  grid-row: 1/3;
  display: flex;
  align-items: center;
  flex-direction: column;
  border-top-right-radius: 100px;
  border-bottom-right-radius: 100px;
    ul {
      list-style: none;
      align-items: center;
      padding: 10px;
    }
}
</style>