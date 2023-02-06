package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Autonomous;//
//    ArrayList<Point> path1 = new ArrayList<>();
//    ArrayList<Point> path2 = new ArrayList<>();
//
//    //        path1.add(new Point(9, 16));  path1.add(new Point(22, 22));
//    path1.addAll(
//            new ArrayList<>(
//                    Arrays.asList(
//                            new Point(4, -63),
//                            new Point(4, -65),
//                            new Point(4, -60),
//                            new Point(4, -52),
//                           new Point(11, -52),
//                            new Point(16, -52),
//                            new Point(20, -52),
//                           new Point(25, -52)
//                            // ,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new
// Point(-20, 55)
//                    )));
//    path2.addAll(
//            new ArrayList<>(
//                    Arrays.asList(
//                            new Point(25, -48),
//                            new Point(15, -48),
//                            new Point(4, -48),
////                            new Point(2, -47),
//                            new Point(6, -65)
//
////                            new Point(23, -8)
//                            // ,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new
// Point(-20, 55)
//                    )));
//// TO TRY
////    path1.addAll(
////            new ArrayList<>(
////                    Arrays.asList(
////                            new Point(2, -50),
//////                            new Point(8, -51),
////                            new Point(10, -50),
////                            new Point(17, -50),
////                            new Point(25, -50)
////                            // ,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new
// Point(-20, 55)
////                    )));
////    path2.addAll(
////            new ArrayList<>(
////                    Arrays.asList(
////                            new Point(25, -50),
////                            new Point(17, -50),
//////                            new Point(2, -47),
////                            new Point(2, -39)
//////                            new Point(23, -8)
////                            // ,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new
// Point(-20, 55)
////                    )));
//

//
//    waitForStart();
//    matchTime.reset();
//    v4bRight.setPosition(0.8);
//    v4bLeft.setPosition(0.2);
//    grabber.raiseTop();
//
//
//    dt.NormalMTP(6, -65 , 0, false,4000);
//  grabber.releaseCone();
//    v4bRight.setPosition(v4bRIn);
//    v4bLeft.setPosition(v4bLIn);
//
////    dt.moveToPosition(
////            2,
////            -39,
////            0,
////            1,
////            1,
////            1,
////            3000,
////            0.5,
////            500);
//   //moveToPosition(1,-25, 0, 10000);
////    telemetry.addLine(dt.getCurrentPosition().toString());
////    telemetry.update();
//    // 2, -51   8, -51    25, -50
//
//    // 2 - 1 / -39 - 51  (1/-90)
//
////    dt.moveToPosition(
////            2,
////            -51,
////            0,
////            1,
////            1,
////            1,
////            30000,
////            0.5,
////            20);
//    //dt.NormalMTP(2, -51, 0, 3000);
//    dt.dropSlides = true;
//    dt.NormalMTP(4, -51, 0, 1500);
//    dt.NormalMTP(24, -51, 0, false, 1000);
//    dt.dropSlides = false;
//
//    grabber.grabCone();
//    sleep(500);
//    grabber.raiseTop();
//
//    v4bRight.setPosition(0.5);
//    v4bLeft.setPosition(0.5);
//    sleep(200);
//    dt.NormalMTP(6, -51, 0, true,2000);
//    v4bRight.setPosition(0.8);
//    v4bLeft.setPosition(0.2);
//    dt.NormalMTP(6, -65 , 0, false,1500);
//
//    sleep(400);
//    grabber.releaseCone();
//    sleep(200);
//    v4bRight.setPosition(v4bRIn);
//    v4bLeft.setPosition(v4bLIn);
//
//    // cycle 2
//    dt.dropSlides2 = true;
//    dt.NormalMTP(5, -51, 0, 1500);
//    dt.NormalMTP(24, -51, 0, false, 1000);
//    dt.dropSlides2 = false;
//
//    // cycle 3
//    grabber.grabCone();
//    sleep(500);
//    grabber.raiseTop();
//    v4bRight.setPosition(0.5);
//    v4bLeft.setPosition(0.5);
//    sleep(200);
//
//    dt.NormalMTP(6, -51, 0, true,2000);
//    v4bRight.setPosition(0.8);
//    v4bLeft.setPosition(0.2);
//    dt.NormalMTP(6, -65 , 0, false,1500);
//    sleep(400);
//    grabber.releaseCone();
//    sleep(200);
//    v4bRight.setPosition(v4bRIn);
//    v4bLeft.setPosition(v4bLIn);
//
//    //cycle 4
//    dt.dropSlides3 = true;
//    dt.NormalMTP(6, -51, 0, 1500);
//    dt.NormalMTP(24, -51, 0, false, 1000);
//    dt.dropSlides3 = false;
//
//    grabber.grabCone();
//    sleep(500);
//    grabber.raiseTop();
//    v4bRight.setPosition(0.5);
//    v4bLeft.setPosition(0.5);
//    sleep(200);
//
//
//    dt.NormalMTP(6, -51, 0, true,2000);
//    v4bRight.setPosition(0.8);
//    v4bLeft.setPosition(0.2);
//    dt.NormalMTP(6, -65 , 0, false,1500);
//    sleep(400);
//    grabber.releaseCone();
//    sleep(200);
//    v4bRight.setPosition(v4bRIn);
//    v4bLeft.setPosition(v4bLIn);
//
//
//    // Cycle 5
//    dt.dropSlides4 = true;
//    dt.NormalMTP(5, -51, 0, 1500);
////    dt.NormalMTP(29, -51, 0, false, 1000);
//    dt.dropSlides4 = false;
//
////    grabber.grabCone();
////    sleep(400);
////    grabber.raiseTop();
////    v4bRight.setPosition(0.5);
////    v4bLeft.setPosition(0.5);
