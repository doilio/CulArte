package com.doiliomatsinhe.cularte.data;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Repository {

    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    // Category Member variables
    private static final String CATEGORIES = "Categorias";
    private static final String VISIBILITY = "visibilidade";
    private static final String FILTER_NAME = "filterNome";
    // Artists Member variables
    private static final String ARTISTS = "Artistas";
    private static final String CATEGORY = "categoria";
    private static final String FILTER_ARTISTIC_NAME = "nomeArtisticoFilter";

    public Query readCategories() {
        return firestoreDB.collection(CATEGORIES)
                .whereEqualTo(VISIBILITY, true)
                .orderBy(FILTER_NAME);
    }

    public Query readArtists(String categoryName) {
        return firestoreDB.collection(ARTISTS)
                .whereEqualTo(VISIBILITY, true)
                .whereEqualTo(CATEGORY, categoryName)
                .orderBy(FILTER_ARTISTIC_NAME);
    }
}
