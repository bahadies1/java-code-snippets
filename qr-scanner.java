 
 
btnScan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            IntentIntegrator integrator = new IntentIntegrator(QRactivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setOrientationLocked(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();

        }
    });
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if(result != null) {
        if(result.getContents() == null) {
            Log.e("Scan*******", "Cancelled scan");
            Toast.makeText(QRactivity.this,"İşlem başarısız",Toast.LENGTH_LONG).show();


        } else {
            Log.e("Tarama", "Tarandı");
            Toast.makeText(QRactivity.this,"İşlem başarılı",Toast.LENGTH_LONG).show();

            tv_qr_readTxt.setText(result.getContents());
            //Toast.makeText(this, "Başarılı: " + result.getContents(), Toast.LENGTH_LONG).show();
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data);
    }

    Map<String, Object> user = new HashMap<>();
    user.put("QRcardID", result.getContents() ) ;

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseFirestore = FirebaseFirestore.getInstance();
    storageReference = FirebaseStorage.getInstance().getReference();
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userid = firebaseUser.getUid();

    databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers")
            .child(userid);
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("QRcardID", result.getContents() ) ;
    databaseReference.updateChildren(hashMap);

}

