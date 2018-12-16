package com.example.mert.mobiluygulamagelitirme.data.db;

public interface Realm {

    interface Add{
        void OnAddSuccess();
        void OnAddFailed();
    }

    interface Delete{
        void OnDeleteSuccess();
        void OnDeleteFailed();
    }

    interface Edit{
        void OnEditSuccess();
        void OnEditFailed();
    }
}
