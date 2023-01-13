ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

        int maxNum = 100;
        List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(maxNum);

        StringBuilder info = new StringBuilder();

        info.append("Services currently running: " + list.size() + "\n\n");
        for(int i=0; i<list.size(); i++){
            info.append(list.get(i).service + "\n\n");
        }

        TextView texView = findViewById(R.id.textView6);
        texView.setMovementMethod(new ScrollingMovementMethod());
        texView.setText(info);
