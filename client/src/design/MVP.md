# Features

### Login
- **Login**: Lets a user sign in with credentials.
- **Register**: Lets a user sign up to use our site.
- **Home**: Authenticated user's home.
- **About**: About page.

### Home 
- **Provider List**: Displays a list of *ALL* providers.
  - **Toggle Provider Menu**: Reveals a menu to allows *authenticated* users to add, edit, or delete providers.
  - **Expandable Provider Card**: Clicking on a provider's name expands the card to show additional options.
    - **View Patients**: Button to view patients associated with the *current* provider.
    - **Details**: Button to view detailed information about the provider.

- **Patient List**: Displays a list of *ALL* patients.
  - **Toggle Patient Menu**: Allows *authenticated* users to edit, or delete patients.
    - **If showing provider's patients**: Allows *authenticated* users to add a patient to the current provider and a new button to show all patients again appears.
    - **Edit patient:** Reveals the patient card where you can update a patient.
    - **Delete Patient**: As an *authenticated* user I can delete a patient.
  - **Search Patients**: As an *authenticated* user I can search to filter by patient name.
  - **Patient Card**: As an *authenticated* user clicking on a patient's name routs the user to the patient's page to show additional information.

- **Add/Edit Patient**: A hidden Form to add or edit patient details.
    - **Updating:** This version of the form contains only *SOME* of a patients properties, this holds value to keep adjustments limited for less sensitive information. Allows for an *authenticated* user to *UPDATE* an existing patient.
    - **Adding:** This version of the form contains *ALL* properties of a patient. Allows for an *authenticated* user to *ADD* a patient.

### Patient View
- This page displays all information associated with the selected patient from the Home view