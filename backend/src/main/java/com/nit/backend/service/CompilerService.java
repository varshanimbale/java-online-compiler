//package com.nit.backend.service;
//
//import java.io.*;
//
//public class CompilerService {
//
//    public static String executeJava(String code) {
//        try {
//            // Create temp folder
//            File dir = new File("temp");
//            if (!dir.exists()) dir.mkdir();
//
//            File file = new File(dir, "Main.java");
//
//            // Write code
//            FileWriter writer = new FileWriter(file);
//            writer.write(code);
//            writer.close();
//
//            StringBuilder result = new StringBuilder();
//            String line;
//
//            // ✅ STEP 1: Compile
//            ProcessBuilder compile = new ProcessBuilder("javac", "Main.java");
//            compile.directory(dir);
//            Process cProcess = compile.start();
//
//            BufferedReader compileError = new BufferedReader(
//                    new InputStreamReader(cProcess.getErrorStream())
//            );
//
//            while ((line = compileError.readLine()) != null) {
//                result.append(line).append("\n");
//            }
//
//            cProcess.waitFor();
//
//            // If compilation error
//            if (result.length() > 0) {
//                return "Compilation Error:\n" + result;
//            }
//
//            // ✅ STEP 2: Run
//            ProcessBuilder run = new ProcessBuilder("java", "-cp", ".", "Main");
//            run.directory(dir);
//            Process rProcess = run.start();
//
//            BufferedReader output = new BufferedReader(
//                    new InputStreamReader(rProcess.getInputStream())
//            );
//
//            BufferedReader error = new BufferedReader(
//                    new InputStreamReader(rProcess.getErrorStream())
//            );
//
//            // Read output
//            while ((line = output.readLine()) != null) {
//                result.append(line).append("\n");
//            }
//
//            // Read runtime errors
//            while ((line = error.readLine()) != null) {
//                result.append(line).append("\n");
//            }
//
//            rProcess.waitFor();
//
//            return result.toString();
//
//        } catch (Exception e) {
//            return "Exception: " + e.getMessage();
//        }
//    }
//}


package com.nit.backend.service;

import java.io.*;

public class CompilerService {

    public static String executeJava(String code, String input) {
        try {
            File dir = new File("temp");
            if (!dir.exists()) dir.mkdir();

            File file = new File(dir, "Main.java");

            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            StringBuilder result = new StringBuilder();
            String line;

            // Compile
            ProcessBuilder compile = new ProcessBuilder("javac", "Main.java");
            compile.directory(dir);
            Process cProcess = compile.start();

            BufferedReader compileError = new BufferedReader(
                    new InputStreamReader(cProcess.getErrorStream())
            );

            while ((line = compileError.readLine()) != null) {
                result.append(line).append("\n");
            }

            cProcess.waitFor();

            if (result.length() > 0) {
                return "Compilation Error:\n" + result;
            }

            // Run
            ProcessBuilder run = new ProcessBuilder("java", "-cp", ".", "Main");
            run.directory(dir);
            Process rProcess = run.start();

            // 🔥 SEND INPUT HERE
            if (input != null && !input.isEmpty()) {
                BufferedWriter writerInput = new BufferedWriter(
                        new OutputStreamWriter(rProcess.getOutputStream())
                );
                writerInput.write(input);
                writerInput.newLine();
                writerInput.flush();
                writerInput.close();
            }

            BufferedReader output = new BufferedReader(
                    new InputStreamReader(rProcess.getInputStream())
            );

            BufferedReader error = new BufferedReader(
                    new InputStreamReader(rProcess.getErrorStream())
            );

            while ((line = output.readLine()) != null) {
                result.append(line).append("\n");
            }

            while ((line = error.readLine()) != null) {
                result.append(line).append("\n");
            }

            rProcess.waitFor();

            return result.toString();

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }
}