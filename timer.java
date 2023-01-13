new CountDownTimer(60000, 1000) {
    public void onTick(long millisUntilFinished) {
        // Used for formatting digit to be in 2 digits only
        NumberFormat f = new DecimalFormat("00");
        long hour = (millisUntilFinished / 3600000) % 24;
        long min = (millisUntilFinished / 60000) % 60;
        long sec = (millisUntilFinished / 1000) % 60;
        btnResendCode.setEnabled(false);
        btnResendCode.setTextColor(getColor(R.color.teal_700));
        txtTimer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
    }
    // When the task is over it will print 00:00:00 there
    public void onFinish() {
        btnResendCode.setTextColor(Color.BLACK);
        txtTimer.setText("Kod hala gelmedi mi?");
        btnResendCode.setEnabled(true);
    }
}.start();

