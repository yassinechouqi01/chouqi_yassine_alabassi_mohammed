package com.example.myprj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView; // Importez TextView pour le nom de l'utilisateur

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Homeactivity extends AppCompatActivity {

    EditText titleEditText, contractTypeEditText, descriptionEditText;
    Spinner categorySpinner, sectorSpinner, citySpinner;
    Button saveButton, signoutButton; // Ajouter le bouton signoutButton
    TextView userNameTextView; // Ajouter le TextView pour le nom de l'utilisateur

    DBhleper2 dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);

        dbHelper = new DBhleper2(this);

        // Initialiser les vues
        titleEditText = findViewById(R.id.titleEditText);
        categorySpinner = findViewById(R.id.category);
        sectorSpinner = findViewById(R.id.sector);
        citySpinner = findViewById(R.id.city);
        contractTypeEditText = findViewById(R.id.cont);
        descriptionEditText = findViewById(R.id.desc);
        saveButton = findViewById(R.id.save);
        signoutButton = findViewById(R.id.signout); // Initialiser le bouton signoutButton
        userNameTextView = findViewById(R.id.userNameTextView); // Initialiser le TextView pour le nom de l'utilisateur

        // Récupérer le nom de l'utilisateur s'il est connecté
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            userNameTextView.setText(personName); // Définir le nom de l'utilisateur dans le TextView
        }

        // Gérer le clic sur le bouton save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sauvegarder les données du formulaire dans la base de données
                String title = titleEditText.getText().toString().trim();
                String category = categorySpinner.getSelectedItem().toString();
                String sector = sectorSpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();
                String contractType = contractTypeEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                boolean isInserted = dbHelper.insertFormData(title, category, sector, city, contractType, description);
                if (isInserted) {
                    // Les données ont été sauvegardées avec succès, obtenir le nombre de formulaires dans la même ville
                    int formCount = dbHelper.getFormCountInCity(city);
                    // Naviguer vers une autre activité pour afficher le nombre de formulaires
                    Intent intent = new Intent(Homeactivity.this, formule.class);
                    intent.putExtra("city", city);
                    intent.putExtra("formCount", formCount);
                    startActivity(intent);
                } else {
                    // Gérer l'échec de l'insertion
                    Log.e("Homeactivity", "Échec de l'insertion des données du formulaire");
                }
            }
        });

        // Gérer le clic sur le bouton de déconnexion
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(); // Appeler la méthode signOut() pour se déconnecter
            }
        });
    }

    // Méthode pour se déconnecter
    private void signOut() {
        // Implémenter la logique de déconnexion ici
        // Par exemple :
        Intent intent = new Intent(Homeactivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Fermer l'activité actuelle pour éviter le retour en arrière
    }
}
