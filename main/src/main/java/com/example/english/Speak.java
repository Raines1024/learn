package com.example.english;

public class Speak {
    //打开本地播放器并播放视频 音频
    public static  void openExe(String file) {
        try
        {
            ///Applications/IntelliJ\ IDEA.app
//            String[] cmd = { "sh", "-c", "open /System/Applications/Music.app /Users/raines/PycharmProjects/untitled/"+file+".mp3 && open /System/Applications/Utilities/Terminal.app" };

            String[] cmd = { "sh", "-c", "open /System/Applications/Music.app /Users/raines/PycharmProjects/untitled/"+file+".mp3 && open /Applications/IntelliJ\\ IDEA.app" };

//            String[] cmd = { "sh", "-c", "open /System/Applications/Music.app /Users/raines/PycharmProjects/untitled/"+file+".mp3" };

//            Process process = Runtime.getRuntime().exec("open /Applications/Audirvana.app /Users/raines/PycharmProjects/untitled/"+file+".mp3 && open /System/Applications/Utilities/Terminal.app");
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            process.destroy();
//            while (process.waitFor() != 0){
//                process.waitFor();
//            }
//            Process terminal = Runtime.getRuntime().exec("open /System/Applications/Utilities/Terminal.app");
//            while (terminal.waitFor() != 0){
//                terminal.waitFor();
//            }

        } catch (Exception e)
        {
            System.out.println("Error!");
        }
    }
    public static void main(String[] args) {


        Speak.openExe("the");

    }
}

