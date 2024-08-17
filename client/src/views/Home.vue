<script>
  import providerService from '../services/provider-service';
  import patientService from '../services/patient-service';

  export default {
    data() {
      return {
        providers: [],
        patients: [],
        searchedPatients: [],
        editingPatient: {},
        currentPCP: {},
        expandedProviderCard: null,
        expandedProviderMenu: false,
        expandedPatientMenu: false,
        isAdding: false,
        isEditing: false,
        isDeleting: false,
        isModifying: false,
        searchTerm: '',
        providerSearchTerm: ''
      }
    },
    created() {
      this.getProviders();
      this.getPatients();
    },

  
    methods: {
      createPatient(editingPatient) {
        patientService.addPatient(editingPatient).then(response => {
          console.log(response);
          console.log(editingPatient);
          this.editingPatient = {};
          this.editingPatient.id = this.currentPCP.id;
          this.getPatientsByPCP(this.currentPCP.id);
          this.toggleAdd();
        });
      },
      updatePatient(id, patient) {
        patientService.updatePatient(id, patient).then(response => {
          console.log(response);
          this.editingPatient = {};
          this.getPatients();
          this.toggleEdit();
        });
      },
      deletePatient(id) {
        patientService.deletePatient(id).then(response => {
          console.log(response);
          if (this.currentPCP.id) {
            this.getPatientsByPCP(this.currentPCP.id);
          } else if (this.searchTerm) {
            this.filterPatients();
          } else {
            this.getPatients();
          }
        });
      },

      getProviders() {
        providerService.getProviders().then(response => {
          this.providers = response.data;
          console.log(this.providers);
        });
      },
      getPatients() {
        patientService.getPatients().then(response => {
          this.patients = response.data;
          console.log(this.patients);
        });
      },
      getPatientsByPCP(id) {
        patientService.getPatientsByPCP(id).then(response => {
          this.patients = response.data;
          console.log(this.patients);
        });
      },
      filterPatients() {        
        if(this.isEditing) {
          if(this.searchTerm.length <= 0){
            this.searchedPatients = this.patients;
          } else {
            patientService.getPatients().then(response => {
              this.searchedPatients = response.data.filter(patient => {
                return (
                  patient.firstName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
                  patient.lastName.toLowerCase().includes(this.searchTerm.toLowerCase())
                );
              });
            });          
          }  
        } else {
          if(this.searchTerm.length <= 0){
            this.getPatients();
          } else {
            patientService.getPatients().then(response => {
              this.patients = response.data.filter(patient => {
                return (
                  patient.firstName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
                  patient.lastName.toLowerCase().includes(this.searchTerm.toLowerCase())
                );
              });
            });
          }
        }
      },
      filterProviders() {
        if(this.providerSearchTerm.length <= 0){
          this.getProviders();
        } else {
        providerService.getProviders().then(response => {
          this.providers = response.data.filter(provider => {
            return (
              provider.firstName.toLowerCase().includes(this.providerSearchTerm.toLowerCase()) ||
              provider.lastName.toLowerCase().includes(this.providerSearchTerm.toLowerCase())
            );
          });
        });
        console.log(this.providerSearchTerm)
      }
      },
    
      toggleProvider(providerId) {
        if (this.expandedProviderCard === providerId) {
          this.expandedProviderCard = null;
        } else {
          this.expandedProviderCard = providerId;
        }
      },
      toggleProviderMenu() {
        this.expandedProviderMenu =! this.expandedProviderMenu;
      },
      togglePatientMenu() {
        this.expandedPatientMenu =! this.expandedPatientMenu;
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
      },
      navigateToPatient(patientId) {
        this.$router.push({ name: 'PatientCard', params: { id: patientId } });
      },
      navigateToProvider(providerId) {
        this.$router.push({ name: 'ProviderCard', params: { id: providerId } });
      }
    }
  };
</script>

<template>
  <div class="container">


    <div class="left-panel">
     
      <div class="left-panel-header">
        <h2 class="title">Providers</h2>
        
        <button @click="toggleProviderMenu(), console.log(expandedProviderMenu)" class="menu">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>
          </svg>
        </button>
        <section v-if="expandedProviderMenu === true" class="expanded-provider-menu">        
          <button>Add</button>
          <button>Delete</button>
        </section>
        <input @input="filterProviders()" type="text" placeholder="Search" v-model="this.providerSearchTerm">
      </div>

      
      
      <article class="provider-panel">
        <section v-for="provider in providers" :key="provider.id" class="provider-card" @click="toggleProvider(provider.id)">
          <p>Dr. {{ provider.firstName }} {{ provider.lastName }}</p>
          <div v-if="expandedProviderCard === provider.id" class="expandable-provider-content">
            <button @click.stop="navigateToProvider(provider.id), console.log(provider)">Details</button>
            <button @click.stop="getPatientsByPCP(provider.id),  currentPCP = provider, editingPatient.primaryCareProviderId = currentPCP.id">View Patients</button>
          </div>          
          
        </section>
      </article>
    </div>


    <div class="right-panel">
      <div class="patient-header">
      <h2>Patients</h2>
      <button @click="togglePatientMenu(), console.log(expandedPatientMenu)" class="menu">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
          <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
        </svg>
      </button>
      <section v-if="expandedPatientMenu === true" class="expanded-patient-menu">
        <button v-if="currentPCP.id != null" @click="getPatients(), currentPCP = {}">View All Patients</button>
        <button v-if="currentPCP.id != null" @click="toggleAdd(), console.log(isAdding)" class="adding-button" :class="{'active-adding-button': isAdding}">Add</button>
        <button @click="toggleEdit(), console.log(isEditing)" class="editing-button" :class="{'active-editing-button': isEditing}">Edit</button>
        <button @click="toggleDelete(), console.log(isDeleting)" class="delete-button" :class="{'active-delete-button': isDeleting}">Delete</button>
      </section>
      <input v-if="!isEditing" @input="filterPatients()" type="text" placeholder="Search" v-model="this.searchTerm">


      
      </div>
      <div class="patient-panel" :class="{ 'full-height': !isModifying}">
        <section v-for="patient in patients" :key="patient.id" class="patient-card">
          <p @click="navigateToPatient(patient.id)" style="cursor: pointer;">{{ patient.lastName }}, {{ patient.firstName }} </p>
          <section v-if="isDeleting" @click="deletePatient(patient.id)" class="delete">
          <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="red" class="bi bi-x-octagon" viewBox="0 0 16 16">
            <path d="M4.54.146A.5.5 0 0 1 4.893 0h6.214a.5.5 0 0 1 .353.146l4.394 4.394a.5.5 0 0 1 .146.353v6.214a.5.5 0 0 1-.146.353l-4.394 4.394a.5.5 0 0 1-.353.146H4.893a.5.5 0 0 1-.353-.146L.146 11.46A.5.5 0 0 1 0 11.107V4.893a.5.5 0 0 1 .146-.353zM5.1 1 1 5.1v5.8L5.1 15h5.8l4.1-4.1V5.1L10.9 1z"/>
            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
          </svg>          
          </section>          
        </section>
      </div>
      <div class="edit-add-container" v-if="isModifying">
        <div v-if="isEditing" class="editing-search-bar">
          <input type="text" placeholder="Search" @input="filterPatients()" v-model="this.searchTerm">
          
        </div>

        <div v-if="isEditing" class="searched-patients">
          <section v-for="searchedPatient in searchedPatients" :key="searchedPatient.id" class="result-card" @click="editingPatient = searchedPatient">          
            <p>{{searchedPatient.lastName}}, {{searchedPatient.firstName }}</p>
          </section>
        </div>
        <div class="edit-add-card" :class="{'full-width': isAdding}">
          <form @submit.prevent>
            <label for="firstName">First Name: </label>
            <input type="text" id="firstName" name="firstName" v-model="editingPatient.firstName">
            <label for="lastName">Last Name: </label>
            <input type="text" id="lastName" name="lastName" v-model="editingPatient.lastName">
            <label v-if="isAdding" for="ssn">SSN: </label>
            <input v-if="isAdding" type="text" id="ssn" name="ssn" v-model="editingPatient.socialSecurityNumber">
            <label for="dob">Date of Birth: </label>            
            <input type="date" id="dob" name="dob" v-model="editingPatient.dateOfBirth">
            <label for="gender">Gender: </label>
            <select id="gender" name="gender" v-model="editingPatient.gender">
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Non-binary">Non-binary</option>
              <option value="Genderqueer">Genderqueer</option>
              <option value="Genderfluid">Genderfluid</option>
              <option value="Agender">Agender</option>
              <option value="Other">Other</option>
            </select>
            <label v-if="isAdding" for="insuranceProvider">Insurance: </label>
            <input v-if="isAdding" type="text" id="insuranceProvider" name="insuranceProvider" v-model="editingPatient.insuranceProvider">
            <button v-if="isEditing" @click="updatePatient(editingPatient.id, editingPatient)">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"/>
              </svg>
            </button>
            <button v-if="isAdding" @click="createPatient(editingPatient), console.log(editingPatient)">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"/>
              </svg>
            </button>
          </form>
        </div>
      </div>

    </div>

  </div>
</template>

<style scoped>





.container {
  display: grid;
  grid-template-columns: 20% 1fr;
  height: 100vh;
  background-color: #292929;
  color: white;
  overflow: hidden;
  transition: background-color 0.3s;
}

.left-panel {
  padding: 20px;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: rgb(29, 29, 29);
  border-right: 1px solid #444;
}

.left-panel-header {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 20px;
  justify-content: space-between;
  align-items: center;
}
.left-panel-header input {
  padding: 5px;
  height: 20px;
  width: 80%;
  border-radius: 50px;
}
.left-panel-header .expanded-provider-menu {
  gap: 20px;
  display: flex;
  flex-direction: column;
  grid-column: 2/3;
  grid-row: 2/4
}
.provider-panel {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 80px;
  margin-top: 10px;
  scrollbar-color: white transparent;
}

.provider-card{
  margin: 15px;
  padding: 5px;
  padding-left: 20%;
  background-color: #292929;
  transition: background-color 0.4s;
  box-shadow: var(--shadow-2);
}

.expandable-provider-content{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.expandable-provider-content button {
  margin-bottom: 10px;
  width: 100px;
  font-size: medium;
}

.provider-card:hover {
  transform: scale(1.05);
  transition: transform 0.3s;
  background-color: #555;
  cursor: pointer;
}


.right-panel {
  display: grid;
  grid-template-rows: 80px 1fr 45% ;
  padding: 20px;
  padding-top: 15px;
  overflow-y: none;
  height: 100vh;
  border-top: 1px solid #444;
}

.patient-header {
  gap: 20px;
  display: flex;
  align-items:center;
}
.patient-header input {
  border-radius: 50px;
  padding: 5px;
  height: 30%;
}
.expanded-patient-menu {
  gap: 20px;
  display: flex;
  flex-direction: row;
}
.expanded-provider-menu{
  display: flex;
  flex-direction: column;
}
.patient-header .menu {
  width: 40px;
}
.patient-panel {
  flex: 1;
  grid-row: 2;
  overflow-y: auto;
  margin-bottom: 10px;
  scrollbar-color: white transparent; 
}

.full-height {
    grid-row: 2/4;
    margin-bottom: 75px;
    padding-bottom: 0px;
}

.patient-card {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin: 15px;
  padding: 5px;
  padding-left: 20px;
  padding-right: 50px;
  background-color: rgb(29, 29, 29);
  transition: background-color 0.4s;
  box-shadow: var(--shadow-2);
}
.patient-card:hover {
  transform: scale(1.01);
  transition: transform 0.3s;
  background-color: #555;
} 

.active-adding-button {
  background-color: green;
  color: white;
}
.active-editing-button {
  background-color: white;
  color: black;
}
.active-delete-button{
  background-color: red;
  color: white;
}
.delete {
  padding-top: 5px;
  cursor: pointer;
}
.edit-add-container {
  margin-left: 15px;
  margin-right: 25px;
  display: grid;
  grid-template-columns: 20% 1fr 1fr;
  height: 82%;
  background-color: rgb(29, 29, 29);
  border-radius: 50px;
}

.edit-add-container input {
  height: 20px;
}

.editing-search-bar {
  background-color: transparent;
  display: flex;
  margin: 20px;
  height: 25px;
  border-radius: 50px;
  grid-column: 1;
  grid-row: 1;
}

.editing-search-bar button, input {
  background-color: transparent;
  color: white;
  border: none;
  box-shadow: none;

}
.editing-search-bar input {
  padding: 5px;
  width: 100%;
}
.searched-patients {
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  grid-column: 1;
  grid-row: 2 / 4;
  margin: 15px;
  padding: 5px;
  transition: background-color 0.4s;
  cursor: pointer;
  scrollbar-color: white transparent; 
}
.result-card{
  box-shadow: var(--shadow-2);
  margin-bottom: 20px;
  border-radius: 50px;
  padding-left: 20%;
  background-color: #292929;
  transition: background-color 0.4s;
  cursor: pointer;
}
.result-card:hover {
  background-color: #555;
}
.edit-add-card {
  box-shadow: var(--shadow-2);
  display: grid;
  grid-template-columns: 1fr 1fr;
  background-color: #292929;
  margin: 20px;
  padding: 20px;
  height: 260px;
  border-radius: 35px;
  grid-column: 2/4;
  grid-row: 1/4;
}
.full-width {
  grid-column: 1/4;
}
.edit-add-card form{
  display: flex;
  flex-direction: column;
  max-width: 250px;
}
.edit-add-card input{
  color: white;
  background-color: #757474;
  border: 1px solid transparent;
  border-radius: 50px;
}
.edit-add-card select{
  color: white;
  background-color: #757474;
  border: 1px solid transparent;
  border-radius: 50px;
}
.edit-add-card button{
  margin-top: 0px;
  padding: 0px;
  grid-column: 2/3;
  background-color: transparent;
  box-shadow: none;
  color: rgb(255, 157, 28);
}


</style>