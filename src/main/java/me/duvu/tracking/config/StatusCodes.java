package me.duvu.tracking.config;

/**
 * @author beou on 5/14/18 16:25
 */
public class StatusCodes {

    /* Digital Input index for explicit STATUS_IGNITION_ON/STATUS_IGNITION_OFF codes */
    public static final int IGNITION_INPUT_INDEX        = 99;

    // ----------------------------------------------------------------------------
    // Reserved status codes: [E0-00 through FF-FF]
    // Groups:
    //      0xF0..  - Generic
    //      0xF1..  - Motion
    //      0xF2..  - Geofence
    //      0xF4..  - Digital input/output
    //      0xF6..  - Sensor input
    //      0xF7..  - Temperature input
    //      0xF9..  - OBC/J1708
    //      0xFD..  - Device status
    // ----------------------------------------------------------------------------

    public static final int STATUS_IGNORE               = -1;

    // ----------------------------------------------------------------------------
    // Reserved: 0x0000 to 0x0FFF
    // No status code: 0x0000

    public static final int STATUS_NONE                 = 0x0000;

    // ----------------------------------------------------------------------------
    // Available: 0x1000 to 0xCFFF
    public static final int STATUS_1000                 = 0x1000;   //  4096
    // ...
    public static final int STATUS_CFFF                 = 0xCFFF;   // 53247

    // ----------------------------------------------------------------------------
    // Reserved: 0xD000 to 0xEFFF
    // ----------------------------------------------------------------------------
    // Motion codes: 0xF100 to 0xF1FF
    // (motion related status codes - typically while ignition is on)

    public static final int STATUS_LOCATION             = 0xF020;   // 61472
    public static final int STATUS_LOCATION_1           = 0xF021;   // 61473
    public static final int STATUS_LOCATION_2           = 0xF022;   // 61474
    public static final int STATUS_LOCATION_3           = 0xF023;   // 61475
    public static final int STATUS_LOCATION_4           = 0xF024;   // 61476
    // Description:
    //      General Status/Location event, typically based on a time-interval.
    //      This status code indicates no more than just the location of the
    //      device at a particular time.

    public static final int STATUS_LAST_LOCATION        = 0xF025;   // 61477
    // Description:
    //      General Status/Location event.  This status code indicates
    //      the last known location of the device (GPS may not be current)


    public static final int STATUS_MOTION_START         = 0xF111;   // 61713
    // Description:
    //      Device start of motion

    public static final int STATUS_MOTION_IN_MOTION     = 0xF112;   // 61714
    public static final int STATUS_MOTION_EN_ROUTE      = 0xF112;   // 61714
    // Description:
    //      Device in-motion interval

    public static final int STATUS_MOTION_STOP          = 0xF113;   // 61715
    // Description:
    //      Device stopped motion

    public static final int STATUS_MOTION_DORMANT       = 0xF114;   // 61716
    // Description:
    //      Device dormant interval (ie. not moving)

    public static final int STATUS_MOTION_STOPPED       = 0xF115;   // 61717
    // Description:
    //      Alternate to Dormant (ie. not moving)

    public static final int STATUS_MOTION_IDLE          = 0xF116;   // 61718
    // Description:
    //      Device idle interval (ie. not moving, but engine may still be on)

    public static final int STATUS_MOTION_IDLE_END      = 0xF117;   // 61719
    // Description:
    //      Use by some devices that send an "end-of-idle" event

    public static final int STATUS_MOTION_EXCESS_IDLE   = 0xF118;   // 61720
    // Description:
    //      Device exceeded idle threshold

    public static final int STATUS_LOW_SPEED            = 0xF119;   // 61721
    public static final int STATUS_MOTION_EXCESS_SPEED  = 0xF11A;   // 61722
    public static final int STATUS_MOTION_OVER_SPEED_1  = 0xF11A;   // 61722
    // Description:
    //      Device exceeded preset speed limit #1
    // OBSOLETE:
    //      Use STATUS_SPEEDING_LIMIT_1

    public static final int STATUS_MOTION_OVER_SPEED_2  = 0xF11B;   // 61723
    // Description:
    //      Device exceeded preset speed limit #2
    // OBSOLETE:
    //      Use STATUS_SPEEDING_LIMIT_2

    public static final int STATUS_MOTION_MOVING        = 0xF11C;   // 61724
    // Description:
    //      Device is moving
    // Notes:
    //      - This status code may be used to indicating that the device was moving
    //      at the time the event was generated. It is typically not associated
    //      with the status codes STATUS_MOTION_START, STATUS_MOTION_STOP, and
    //      STATUS_MOTION_IN_MOTION, and may be used independently of these codes.
    //      - This status code is typically used for devices that need to periodically
    //      report that they are moving, apart from the standard start/stop/in-motion
    //      events.

    public static final int STATUS_MOTION_STOP_PENDING  = 0xF11D;   // 61725
    // Description:
    //      Motion changed to stop, waiting for official "Stop" event

    public static final int STATUS_MOTION_CHANGE        = 0xF11E;   // 61726
    // Description:
    //      Motion state change (from moving to stopped, or stopped to moving)

    public static final int STATUS_MOTION_HEADING       = 0xF11F;   // 61727
    // Description:
    //      Device motion changed direction/heading

    public static final int STATUS_MOTION_ACCELEROMETER = 0xF120;   // 61728
    // Description:
    //      Device motion detected due to accelerometer

    public static final int STATUS_MOTION_ACCELERATION  = 0xF123;   // 61731
    // Description:
    //      Device motion acceleration change

    public static final int STATUS_MOTION_DECELERATION  = 0xF126;   // 61734
    // Description:
    //      Device motion acceleration change

    public static final int STATUS_TRACK_START          = 0xF181;   // 61825
    // Description:
    //      Tracking start

    public static final int STATUS_TRACK_LOCATION       = 0xF182;   // 61826
    // Description:
    //      Periodic location event while tracking

    public static final int STATUS_TRACK_STOP           = 0xF183;   // 61827
    // Description:
    //      Tracking stop

    // ----------------------------------------------------------------------------
    // Geofence: 0xF200 to 0xF2FF

    public static final int STATUS_GEOFENCE_ARRIVE      = 0xF210;   // 61968
    // Description:
    //      Device arrived at geofence/geozone

    public static final int STATUS_CORRIDOR_ARRIVE      = 0xF213;   // 61971
    // Description:
    //      Device entered GeoCorridor

    public static final int STATUS_JOB_ARRIVE           = 0xF215;   // 61973
    // Description:
    //      Device arrived at job-site (typically driver entered)

    public static final int STATUS_GEOFENCE_DEPART      = 0xF230;   // 62000
    // Description:
    //      Device departed geofence/geozone

    public static final int STATUS_CORRIDOR_DEPART      = 0xF233;   // 62003
    // Description:
    //      Device exited GeoCorridor (alternate for STATUS_CORRIDOR_VIOLATION)

    public static final int STATUS_JOB_DEPART           = 0xF235;   // 62005
    // Description:
    //      Device departed job-site (typically driver entered)

    public static final int STATUS_GEOFENCE_VIOLATION   = 0xF250;   // 62032
    // Description:
    //      Geofence violation

    public static final int STATUS_CORRIDOR_VIOLATION   = 0xF258;   // 62040
    // Description:
    //      GeoCorridor violation

    public static final int STATUS_GEOFENCE_ACTIVE      = 0xF270;   // 62064
    // Description:
    //      Geofence now active

    public static final int STATUS_CORRIDOR_ACTIVE      = 0xF278;   // 62072
    // Description:
    //      GeoCorridor now active

    public static final int STATUS_GEOFENCE_INACTIVE    = 0xF280;   // 62080
    // Description:
    //      Geofence now inactive

    public static final int STATUS_CORRIDOR_INACTIVE    = 0xF288;   // 62088
    // Description:
    //      Geofence now inactive

    public static final int STATUS_GEOBOUNDS_ENTER      = 0xF2A0;   // 62112
    // Description:
    //      Device has entered a state/boundary

    public static final int STATUS_GEOBOUNDS_EXIT       = 0xF2B0;   // 62128
    // Description:
    //      Device has exited a state/boundary

    public static final int STATUS_PARKED               = 0xF2C0;   // 62144
    // Description:
    //      Device has parked

    public static final int STATUS_EXCESS_PARK          = 0xF2C3;   // 62147
    // Description:
    //      Device has exceeded a parked threshold

    public static final int STATUS_UNPARKED             = 0xF2C6;   // 62150
    // Description:
    //      Device has unparked

    // ----------------------------------------------------------------------------
    // Digital input/output (state change): 0xF400 to 0xF4FF

    public static final int STATUS_INPUT_STATE          = 0xF400;   // 62464
    // Description:
    //      Current input state change

    public static final int STATUS_IGNITION_ON          = 0xF401;   // 62465
    // Description:
    //      Ignition turned ON
    // Notes:
    //      - This status code may be used to indicate that the ignition input
    //      turned ON.

    public static final int STATUS_INPUT_ON             = 0xF402;   // 62466
    // Description:
    //      Input turned ON

    public static final int STATUS_IGNITION_OFF         = 0xF403;   // 62467
    // Description:
    //      Ignition turned OFF
    // Notes:
    //      - This status code may be used to indicate that the ignition input
    //      turned OFF.

    public static final int STATUS_INPUT_OFF      = 0xF404;   // 62468

    public static final int STATUS_ENGINE_START         = 0xF40C;   // 62476
    // Description:
    //      Engine started
    // Notes:
    //      - This status code may be used to specifically indicate that the engine
    //      has been started.  This may be different from "Ignition On", if the
    //      STATUS_IGNITION_ON also indicates when only the "accessory" loop of
    //      the ignition state is on.

    public static final int STATUS_ENGINE_STOP          = 0xF40D;   // 62477
    // Description:
    //      Engine stopped
    // Notes:
    //      - This status code may be used to specifically indicate that the engine
    //      has been stopped.  This may be different from "Ignition Off", if the
    //      STATUS_IGNITION_OFF also indicates when only the "accessory" loop of
    //      the ignition state is off.

    // Description:
    //      Digital input state changed to ON

    public static final int STATUS_DOOR_OPEN_0          = 0xF434;   // 62516
    public static final int STATUS_DOOR_OPEN_1          = 0xF435;   // 62517
    public static final int STATUS_DOOR_OPEN_2          = 0xF436;   // 62518
    // Description:
    //      Door open
    // Notes:
    //      - This status code may be used to specifically indicate that a door
    //      has been opened.  (alternatively a STATUS_INPUT_ON_xx status code
    //      could be used, with the description changed to "door open").

    public static final int STATUS_SEATBELT_ON_0        = 0xF438;   // 62520
    public static final int STATUS_SEATBELT_ON_1        = 0xF439;   // 62521
    // Description:
    //      Setbelt on/latched
    // Notes:
    //      - This status code may be used to specifically indicate that a seatbelt
    //      has been latched.  (alternatively a STATUS_INPUT_ON_xx status code
    //      could be used, with the description changed to "setbelt on").


    public static final int STATUS_DOOR_CLOSE_0         = 0xF454;   // 62548
    public static final int STATUS_DOOR_CLOSE_1         = 0xF455;   // 62549
    public static final int STATUS_DOOR_CLOSE_2         = 0xF456;   // 62550
    // Description:
    //      Door closed
    // Notes:
    //      - This status code may be used to specifically indicate that a door
    //      has been closed.  (alternatively a STATUS_INPUT_ON_xx status code
    //      could be used, with the description changed to "door closed").

    public static final int STATUS_SEATBELT_OFF_0       = 0xF458;   // 62552
    public static final int STATUS_SEATBELT_OFF_1       = 0xF459;   // 62553
    // Description:
    //      Setbelt on/latched
    // Notes:
    //      - This status code may be used to specifically indicate that a seatbelt
    //      has been latched.  (alternatively a STATUS_INPUT_ON_xx status code
    //      could be used, with the description changed to "setbelt on").

    public static final int STATUS_ELAPSED_00           = 0xF4A0;   // 62624
    public static final int STATUS_ELAPSED_01           = 0xF4A1;
    public static final int STATUS_ELAPSED_02           = 0xF4A2;
    public static final int STATUS_ELAPSED_03           = 0xF4A3;
    public static final int STATUS_ELAPSED_04           = 0xF4A4;
    public static final int STATUS_ELAPSED_05           = 0xF4A5;
    public static final int STATUS_ELAPSED_06           = 0xF4A6;
    public static final int STATUS_ELAPSED_07           = 0xF4A7;   // 62631
    // Description:
    //      Elapsed time
    //      0xFAA8 through 0xFAAF reserved

    // ----------------------------------------------------------------------------
    // Miscellaneous

    public static final int STATUS_LOGIN                = 0xF811;   // 63505
    // Description:
    //      Generic 'login'

    public static final int STATUS_LOGOUT               = 0xF812;   // 63506
    // Description:
    //      Generic 'logout'

    // --------------------------------

    public static final int STATUS_ARMED                = 0xF817;   // 63511
    // Description:
    //      Generic 'armed'

    public static final int STATUS_DISARMED             = 0xF818;   // 63512
    // Description:
    //      Generic 'disarmed'

    // --------------------------------

    public static final int STATUS_ENTITY_STATE         = 0xF820;   // 63520
    // Description:
    //      General Entity state

    public static final int STATUS_ENTITY_CONNECT       = 0xF821;   // 63521
    // Description:
    //      General Entity Connect/Hook/On

    public static final int STATUS_ENTITY_DISCONNECT    = 0xF822;   // 63522
    // Description:
    //      General Entity Disconnect/Drop/Off

    public static final int STATUS_ENTITY_INVENTORY     = 0xF823;   // 63523
    // Description:
    //      General Entity Inventory

    // --------------------------------
    public static final int STATUS_TRAILER_STATE        = 0xF824;   // 63524
    // Description:
    //      Trailer State change

    public static final int STATUS_TRAILER_HOOK         = 0xF825;   // 63525
    // Description:
    //      Trailer Hook

    public static final int STATUS_TRAILER_UNHOOK       = 0xF826;   // 63526
    // Description:
    //      Trailer Unhook

    public static final int STATUS_TRAILER_INVENTORY    = 0xF827;   // 63527
    // Description:
    //      Trailer Inventory

    // --------------------------------

    public static final int STATUS_RFID_STATE           = 0xF828;   // 63528
    // Description:
    //      RFID State change

    public static final int STATUS_RFID_CONNECT         = 0xF829;   // 63529
    // Description:
    //      RFID Connect/InRange

    public static final int STATUS_RFID_DISCONNECT      = 0xF82A;   // 63530
    // Description:
    //      RFID Disconnect/OutOfRange

    public static final int STATUS_RFID_INVENTORY       = 0xF82B;   // 63531
    // Description:
    //      RFID Inventory

    // --------------------------------

    public static final int STATUS_PERSON_ENTER         = 0xF82C;   // 63532
    // Description:
    //      Passenger/Person Enter/Embark

    public static final int STATUS_PERSON_EXIT          = 0xF82D;   // 63533
    // Description:
    //      Passenger/Person Exit/Disembark

    public static final int STATUS_PERSON_INVENTORY     = 0xF82E;   // 63534
    // Description:
    //      Passenger/Person Inventory

    public static final int STATUS_PERSON_STATE         = 0xF82F;   // 63535
    // Description:
    //      Passenger/Person State change

    // --------------------------------

    public static final int STATUS_ACK                  = 0xF831;   // 63537
    // Description:
    //      Acknowledge

    public static final int STATUS_NAK                  = 0xF832;   // 63538
    // Description:
    //      Negative Acknowledge

    // --------------------------------

    public static final int STATUS_COMMAND_ACK          = 0xF833;   // 63539
    // Description:
    //      General DeviceCommand Acknowledge

    public static final int STATUS_COMMAND_NAK          = 0xF834;   // 63540
    // Description:
    //      General DeviceCommand Negative Acknowledge

    // --------------------------------

    public static final int STATUS_DUTY_ON              = 0xF837;   // 63543
    // Description:
    //      Duty condition activated

    public static final int STATUS_DUTY_OFF             = 0xF838;   // 63544
    // Description:
    //      Duty condition deactivated

    // --------------------------------

    public static final int STATUS_PANIC_ON             = 0xF841;   // 63553
    // Description:
    //      Panic/SOS condition activated

    public static final int STATUS_PANIC_OFF            = 0xF842;   // 63554
    // Description:
    //      Panic/SOS condition deactivated

    // --------------------------------

    public static final int STATUS_ALARM_ON             = 0xF847;   // 63559
    // Description:
    //      General Alarm condition activated

    public static final int STATUS_ALARM_OFF            = 0xF848;   // 63560
    // Description:
    //      General Alarm condition deactivated

    // --------------------------------

    public static final int STATUS_ASSIST_ON            = 0xF851;   // 63569
    // Description:
    //      Assist condition activated

    public static final int STATUS_ASSIST_OFF           = 0xF852;   // 63570
    // Description:
    //      Assist condition deactivated

    // --------------------------------

    public static final int STATUS_MANDOWN_ON           = 0xF855;   // 63573
    // Description:
    //      man-down activated

    public static final int STATUS_MANDOWN_OFF          = 0xF856;   // 63574
    // Description:
    //      man-down deactivated

    // --------------------------------

    public static final int STATUS_MEDICAL_ON           = 0xF861;   // 63585
    // Description:
    //      Medical Call condition activated

    public static final int STATUS_MEDICAL_OFF          = 0xF862;   // 63586
    // Description:
    //      Medical Call condition deactivated

    // --------------------------------

    public static final int STATUS_DRIVER_FATIGUE       = 0xF868;   // 63592
    // Description:
    //      driver fatigued

    public static final int STATUS_DRIVER_SLEEP         = 0xF869;   // 63593
    // Description:
    //      driver sleep

    public static final int STATUS_DRIVER_WAKE          = 0xF86A;   // 63594
    // Description:
    //      driver wake

    // --------------------------------

    public static final int STATUS_TOWING_START         = 0xF871;   // 63601
    // Description:
    //      Vehicle started to be towed

    public static final int STATUS_TOWING_STOP          = 0xF872;   // 63602
    // Description:
    //      Vehicle stopped being towed

    // --------------------------------

    public static final int STATUS_INTRUSION_ON         = 0xF881;   // 63617
    // Description:
    //      Intrusion detected

    public static final int STATUS_INTRUSION_OFF        = 0xF882;   // 63618
    // Description:
    //      Intrusion aborted

    // --------------------------------

    public static final int STATUS_TAMPER_ON            = 0xF885;   // 63621
    // Description:
    //      Tamper detected

    public static final int STATUS_TAMPER_OFF           = 0xF886;   // 63622
    // Description:
    //      Tamper aborted

    // --------------------------------

    public static final int STATUS_BREACH_ON            = 0xF889;   // 63625
    // Description:
    //      Breach detected

    public static final int STATUS_BREACH_OFF           = 0xF88A;   // 63626
    // Description:
    //      Breach aborted

    // --------------------------------

    public static final int STATUS_VIBRATION_ON         = 0xF891;   // 63633
    // Description:
    //      Vibration on

    public static final int STATUS_VIBRATION_OFF        = 0xF892;   // 63634
    // Description:
    //      Vibration off

    // --------------------------------

    public static final int STATUS_DOOR_LOCK            = 0xF895;   // 63637
    // Description:
    //      Door lock

    public static final int STATUS_DOOR_UNLOCK          = 0xF896;   // 63638
    // Description:
    //      Door unlock

    // --------------------------------

    public static final int STATUS_PTO_ON               = 0xF899;   // 63641
    // Description:
    //      PTO on

    public static final int STATUS_PTO_OFF              = 0xF89A;   // 63642
    // Description:
    //      PTO off

    // --------------------------------

    public static final int STATUS_ONLINE               = 0xF89D;   // 63645
    // Description:
    //      Online

    public static final int STATUS_OFFLINE              = 0xF89E;   // 63646
    // Description:
    //      Offline

    // ----------------------------------------------------------------------------
    // Entity status: 0xF8E0 to 0xF8FF

    // ----------------------------------------------------------------------------
    // OBC/J1708/CANBUS status: 0xF900 to 0xF9FF

    public static final int STATUS_OBD_INFO_0           = 0xF900;   //
    public static final int STATUS_OBD_INFO_1           = 0xF901;
    public static final int STATUS_OBD_INFO_2           = 0xF902;
    public static final int STATUS_OBD_INFO_3           = 0xF903;
    public static final int STATUS_OBD_INFO_4           = 0xF904;   //
    public static final int STATUS_OBD_INFO_5           = 0xF905;   //
    public static final int STATUS_OBD_INFO_6           = 0xF906;   //
    public static final int STATUS_OBD_INFO_7           = 0xF907;   //
    // Description:
    //      OBC/J1708 information packet

    public static final int STATUS_OBD_CONNECT          = 0xF90F;   // 63759
    // Description:
    //      OBD connected to vehicle

    public static final int STATUS_OBD_DISCONNECT       = 0xF910;   // 63760
    // Description:
    //      OBD disconnected from vehicle

    public static final int STATUS_OBD_FAULT            = 0xF911;   // 63761
    // Description:
    //      OBC/J1708 fault code occurred.

    public static final int STATUS_CHECK_ENGINE         = 0xF915;   //
    // Description:
    //      Malfunction Indicator Lamp (MIL)

    public static final int STATUS_OBD_RANGE            = 0xF920;   //
    // Description:
    //      Generic OBC/J1708 value out-of-range

    public static final int STATUS_OBD_RPM_RANGE        = 0xF922;   // 63778
    // Description:
    //      OBC/J1708 RPM out-of-range

    public static final int STATUS_OBD_FUEL_RANGE       = 0xF924;   // 63780
    // Description:
    //      OBC/J1708 Fuel level out-of-range (ie. to low)
    // Notes:
    //      - This code can also be used to indicate possible fuel theft.

    public static final int STATUS_OBD_OIL_RANGE        = 0xF926;   // 63782
    // Description:
    //      OBC/J1708 Oil level out-of-range (ie. to low)

    public static final int STATUS_OBD_TEMP_RANGE       = 0xF928;   // 63784
    // Description:
    //      OBC/J1708 Temperature out-of-range

    public static final int STATUS_OBD_LOAD_RANGE       = 0xF92A;   // 63786
    // Description:
    //      OBC/J1708 Engine-Load out-of-range

    public static final int STATUS_OBD_COOLANT_RANGE    = 0xF92C;   // 63788
    // Description:
    //      OBC/J1708 Engine coolent level out-of-range

    public static final int STATUS_EXCESS_BRAKING       = 0xF930;   // 63792
    public static final int STATUS_EXCESS_BRAKING_2     = 0xF931;   // 63793
    public static final int STATUS_EXCESS_BRAKING_3     = 0xF932;   // 63794
    // Description:
    //      Excessive/Harsh deceleration detected

    public static final int STATUS_EXCESS_CORNERING     = 0xF937;   // 63799
    public static final int STATUS_EXCESS_CORNERING_2   = 0xF938;   // 63800
    public static final int STATUS_EXCESS_CORNERING_3   = 0xF939;   // 63801
    // Description:
    //      Excessive lateral acceleration detected
    //      Also called "hard turning", "veer alarm", "lateral acceleration"

    public static final int STATUS_IMPACT               = 0xF941;   // 63809
    public static final int STATUS_IMPACT_DATA_1        = 0xF942;   // 63810
    public static final int STATUS_IMPACT_DATA_2        = 0xF943;   // 63811
    public static final int STATUS_IMPACT_DATA_3        = 0xF944;   // 63812
    // Description:
    //      Excessive acceleration/deceleration detected

    public static final int STATUS_FREEFALL             = 0xF945;   // 63813
    // Description:
    //      Freefall detected

    public static final int STATUS_FUEL_REFILL          = 0xF951;   // 63825
    // Description:
    //      Fuel refill detected

    public static final int STATUS_FUEL_THEFT           = 0xF952;   // 63826
    // Description:
    //      Fuel theft detected

    public static final int STATUS_LOW_FUEL             = 0xF954;   // 63828
    // Description:
    //      Low fuel alert

    public static final int STATUS_FUEL_DIRTY           = 0xF95A;   // 63834
    // Description:
    //      Fuel contaminated/dirty

    public static final int STATUS_FUEL_SENSOR          = 0xF95E;   // 63838
    // Description:
    //      Fuel sensor failed/bad

    public static final int STATUS_EXCESS_ACCEL         = 0xF960;   // 63840
    public static final int STATUS_EXCESS_ACCEL_2       = 0xF961;   // 63841
    public static final int STATUS_EXCESS_ACCEL_3       = 0xF962;   // 63842
    // Description:
    //      Excessive acceleration detected

    // ----------------------------------------------------------------------------
    // Device custom status

    public static final int STATUS_DAY_SUMMARY          = 0xFA00;   // 64000
    // Description:
    //      End-Of-Day Summary

    public static final int STATUS_TRIP_SUMMARY         = 0xFA40;   // 64064
    // Description:
    //      End-Of-Day Summary

    // ----------------------------------------------------------------------------
    // Tire Pressure/Temperature

    public static final int STATUS_TIRE_TEMP_RANGE      = 0xFBA0;   // 64416
    // Description:
    //      Tire Temperature out-of-range

    public static final int STATUS_TIRE_PRESSURE_RANGE  = 0xFBB0;   // 64432
    // Description:
    //      Tire Pressure out-of-range

    public static final int STATUS_TIRE_PRESSURE_LOW    = 0xFBC0;   // 64448
    // Description:
    //      Tire Pressure low

    public static final int STATUS_TIRE_BATTERY_LOW     = 0xFBD0;   // 64464
    // Description:
    //      Tire sensor battery low

    // ----------------------------------------------------------------------------
    // Internal device status

    public static final int STATUS_IP_ADDRESS           = 0xFD01;   // 64769
    // Description:
    //      IP Address changed

    public static final int STATUS_SIM_CARD             = 0xFD03;   // 64771
    // Description:
    //      SIM Card changed

    public static final int STATUS_BATTERY_VOLTS        = 0xFD0A;   // 64778
    // Description:
    //      Battery voltage

    public static final int STATUS_BACKUP_VOLTS         = 0xFD0C;   // 64780
    // Description:
    //      Backup Battery voltage

    public static final int STATUS_BATT_CHARGE_ON       = 0xFD0E;   // 64782
    // Description:
    //      Battery charging on

    public static final int STATUS_BATT_CHARGE_OFF      = 0xFD0F;   // 64783
    // Description:
    //      Battery charging off

    public static final int STATUS_LOW_BATTERY          = 0xFD10;   // 64784
    // Description:
    //      Low battery indicator

    public static final int STATUS_BATTERY_LEVEL        = 0xFD11;   // 64785
    // Description:
    //      Battery indicator

    public static final int STATUS_POWER_FAILURE        = 0xFD13;   // 64787
    // Description:
    //      Power failure indicator (or running on internal battery)

    public static final int STATUS_POWER_ALARM          = 0xFD14;   // 64788
    // Description:
    //      Power alarm condition

    public static final int STATUS_POWER_RESTORED       = 0xFD15;   // 64789
    // Description:
    //      Power restored (after previous failure)

    public static final int STATUS_POWER_OFF            = 0xFD17;   // 64791
    // Description:
    //      Power failure indicator (or running on internal battery)

    public static final int STATUS_POWER_ON             = 0xFD19;   // 64793
    // Description:
    //      Power restored (after previous failure)

    public static final int STATUS_GPS_EXPIRED          = 0xFD21;   // 64801
    // Description:
    //      GPS fix expiration detected

    public static final int STATUS_GPS_FAILURE          = 0xFD22;   // 64802
    // Description:
    //      GPS receiver failure detected

    public static final int STATUS_GPS_ANTENNA_OPEN     = 0xFD23;   // 64803
    // Description:
    //      GPS antenna open detected

    public static final int STATUS_GPS_ANTENNA_SHORT    = 0xFD24;   // 64804
    // Description:
    //      GPS antenna open detected

    public static final int STATUS_GPS_JAMMING          = 0xFD25;   // 64805
    // Description:
    //      GPS receiver jamming detected

    public static final int STATUS_GPS_RESTORED         = 0xFD26;   // 64806
    // Description:
    //      GPS receiver restore detected

    public static final int STATUS_GPS_LOST             = 0xFD27;   // 64807
    // Description:
    //      GPS receiver unable to obtain fix

    public static final int STATUS_DIAGNOSTIC           = 0xFD30;   // 64816
    // Description:
    //      General Diagnostic message

    public static final int STATUS_CONNECTION_FAILURE   = 0xFD31;   // 64817
    // Description:
    //      Modem/GPRS/CDMA Connection failure detected

    public static final int STATUS_CONNECTION_RESTORED  = 0xFD32;   // 64818
    // Description:
    //      Modem/GPRS/CDMA Connection restore detected

    public static final int STATUS_MODEM_FAILURE        = 0xFD33;   // 64819
    // Description:
    //      Modem failure detected

    public static final int STATUS_INTERNAL_FAILURE     = 0xFD35;   // 64821
    // Description:
    //      Internal failure detected

    public static final int STATUS_MODEM_JAMMING        = 0xFD39;   // 64825
    // Description:
    //      Modem detected jamming

    // Description:
    //      Modem no longer jamming

    public static final int STATUS_CONFIG_RESET         = 0xFD41;   // 64833
    // Description:
    //      Configuration reset

    public static final int STATUS_CONFIG_START         = 0xFD42;   // 64834
    // Description:
    //      Configuration starting

    public static final int STATUS_CONFIG_COMPLETE      = 0xFD43;   // 64835
    // Description:
    //      Configuration complete/finished

    public static final int STATUS_CONFIG_FAILED        = 0xFD44;   // 64836
    // Description:
    //      Configuration failed

    public static final int STATUS_SHUTDOWN             = 0xFD45;   // 64837
    // Description:
    //      device shutdown

    public static final int STATUS_SHUTDOWN_CANCELLED   = 0xFD47;   // 64839
    // Description:
    //      device shutdown

    public static final int STATUS_SUSPEND              = 0xFD48;   // 64840
    // Description:
    //      device sleep/suspend

    public static final int STATUS_RESUME               = 0xFD4A;   // 64842
    // Description:
    //      device resume

    public static final int STATUS_ROAMING_ON           = 0xFD51;   // 64849
    // Description:
    //      modem roaming ON

    public static final int STATUS_ROAMING_OFF          = 0xFD52;   // 64850
    // Description:
    //      modem roaming OFF

    public static final int STATUS_ROAMING_UNKNOWN      = 0xFD53;   // 64851
    // Description:
    //      modem roaming unknown
}
