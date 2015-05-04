package com.khonsu.enroute;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GoogleMapsDurationGetterTest {

	@Test
	public void testThatCanGetEstimateBetweenTwoLocations() throws UnableToGetEstimatedJourneyTimeException, MalformedURLException {
		String duration = "2 hours and 30 minutes";
		String distance = "24601";
		String originLat = "51";
		String originLong = "-0.1";
		String destination = "Birmingham";
		URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=%s&mode=%s", originLat, originLong, destination, "bicycling"));

		FakeUrlAccessor fakeUrlAccessor = new FakeUrlAccessor(url, distance, duration);
		GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(fakeUrlAccessor);
		Estimate estimate = googleMapsDurationGetter.getJourneyEstimate(originLat, originLong, destination, ModeOfTransport.BIKE);
		assertEquals(duration, estimate.duration);
		assertEquals(distance, estimate.distance);
	}

	private class FakeUrlAccessor extends UrlAccessor{
		Map<URL, String> urlToResponse = new HashMap<>();
		public FakeUrlAccessor(URL url, String distance, String duration) {
			urlToResponse.put(url, generateFakeResponse(distance, duration));
		}

		private String generateFakeResponse(String distance, String duration) {
			return String.format("{ \"routes\" : [ { \"bounds\" : { \"northeast\" : { \"lat\" : 52.9562006, \"lng\" : -0.09642869999999999 }, \"southwest\" : { \"lat\" : 51.0001045, \"lng\" : -1.3106247 } }, \"copyrights\" : \"Map data ©2015 Google\", \"legs\" : [ { \"distance\" : { \"text\" : \"10 mi\", \"value\" : %s }, \"duration\" : { \"text\" : \"%s\", \"value\" : 11182 }, \"end_address\" : \"Nottingham, Nottingham, UK\", \"end_location\" : { \"lat\" : 52.9547343, \"lng\" : -1.1582607 }, \"start_address\" : \"8 Fairford Close, Haywards Heath, West Sussex RH16 3EF, UK\", \"start_location\" : { \"lat\" : 51.0001045, \"lng\" : -0.0996874 }, \"steps\" : [ { \"distance\" : { \"text\" : \"85 m\", \"value\" : 85 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 18 }, \"end_location\" : { \"lat\" : 51.0008443, \"lng\" : -0.09957049999999999 }, \"html_instructions\" : \"Head \\u003cb\\u003enortheast\\u003c/b\\u003e on \\u003cb\\u003eFairford Cl\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"s}gvH`nRA@C?CAAAACAE]@]@O?MAIC_@I\" }, \"start_location\" : { \"lat\" : 51.0001045, \"lng\" : -0.0996874 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"77 m\", \"value\" : 77 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 30 }, \"end_location\" : { \"lat\" : 51.00109250000001, \"lng\" : -0.1005985 }, \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e to stay on \\u003cb\\u003eFairford Cl\\u003c/b\\u003e\", \"maneuver\" : \"turn-left\", \"polyline\" : { \"points\" : \"gbhvHhmRE\\\\Il@CPId@ShA\" }, \"start_location\" : { \"lat\" : 51.0008443, \"lng\" : -0.09957049999999999 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.7 km\", \"value\" : 704 }, \"duration\" : { \"text\" : \"2 mins\", \"value\" : 94 }, \"end_location\" : { \"lat\" : 51.0067307, \"lng\" : -0.0961851 }, \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eOathall Rd/B2112\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eGo through 1 roundabout\\u003c/div\\u003e\", \"maneuver\" : \"turn-right\", \"polyline\" : { \"points\" : \"ychvHvsRECa@Q_@SYS_@WeAw@iByAwDwC}@s@mB{A?@A??@A??@A?A?A??AA??AA??A?A?AA??AOQyAiA{FoEMKEEi@YCAEAEAG?E@\" }, \"start_location\" : { \"lat\" : 51.00109250000001, \"lng\" : -0.1005985 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.5 km\", \"value\" : 514 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 52 }, \"end_location\" : { \"lat\" : 51.0059057, \"lng\" : -0.1033024 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e1st\\u003c/b\\u003e exit onto \\u003cb\\u003eSydney Rd/B2028\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"agivHdxQ?@?@?@?B?@?@A@?@?BA@?BBFBBBF@FBH@JZbDl@lFl@nFD\\\\Dh@@L?H@J?R?\\\\?f@Al@?vEA|@@p@?H?VBZ@TFl@\" }, \"start_location\" : { \"lat\" : 51.0067307, \"lng\" : -0.0961851 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.4 km\", \"value\" : 414 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 43 }, \"end_location\" : { \"lat\" : 51.00906519999999, \"lng\" : -0.1036196 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e4th\\u003c/b\\u003e exit onto \\u003cb\\u003eMill Green Rd\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"}aivHrdS@?@?@A@?@?@??@@?@?@@@??@@??@@@@@?@@??@@@?@?@?@@B?@?@?@?@?@?@?@?@?@?@?@?@A@?@?@?@A@?@A@?@A??@A@A??@A?A@A?A?A?A?A?A??AA?A??AA??AA??AA??AAA?AAA?A?AAA?A?AAA?A?A?CKE_@GSAM?K@IBw@RmA\\\\o@PKBMBI@G?G?I?KAUCm@I]GaAMOCK?eA?\" }, \"start_location\" : { \"lat\" : 51.0059057, \"lng\" : -0.1033024 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"1.1 km\", \"value\" : 1086 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 81 }, \"end_location\" : { \"lat\" : 51.0159963, \"lng\" : -0.11289 }, \"html_instructions\" : \"\\u003cb\\u003eMill Green Rd\\u003c/b\\u003e turns \\u003cb\\u003eleft\\u003c/b\\u003e and becomes \\u003cb\\u003eBalcombe Rd\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"uuivHrfS@p@AX?RAb@AV?NAN?DADAJCJCHCDCFC@A@EBE?kABW?G?E?QDEBE@IFOJg@X]PGBKDg@R[NQHMHsE|C}@^w@Ti@VMFSNQLU`@Q\\\\KTMTUn@O\\\\GJILW`@OVMZ[|@YhAYhAAHQbAS~@c@dBO\\\\Q^QZU\\\\ABW\\\\S\\\\CDMb@GXGVOd@Od@IPCHYl@\" }, \"start_location\" : { \"lat\" : 51.00906519999999, \"lng\" : -0.1036196 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"3.1 km\", \"value\" : 3059 }, \"duration\" : { \"text\" : \"3 mins\", \"value\" : 188 }, \"end_location\" : { \"lat\" : 51.041812, \"lng\" : -0.1162764 }, \"html_instructions\" : \"Continue onto \\u003cb\\u003eBorde Hill Ln\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"_akvHp`UKNW`@KJ_@ZSNMHQFODI@G@O@a@@e@?g@?a@E[EkAU_AMsFc@OC_@GgA[oBi@YGa@Ga@Ai@Fs@J_ADC?O?]A_@A_@Gm@Oc@Og@UKGYKKE_@EU?_@CG?a@@IAQAIAKCc@Ka@OKEQEMCOCOCMA_@Cs@C}@@q@Bo@?]Ca@Kk@Ue@Yq@a@e@Qs@QwAUC?u@AaAFu@Di@@E?s@IUEEAWKECIGGIKQKOGGGEKGA?k@Gk@G_BSI?YAe@By@JwAZyAViANIBYHc@R_AZs@RcAZu@VcAl@sCrBGF_@f@KPe@bAWh@_@`AQl@Sn@Qx@Ux@Uh@Uh@]p@_@p@c@l@[^ONMJSHKDMBYFU@k@BY@gBJoANm@Bk@CqA?_B@e@Ck@IUK]ESC\" }, \"start_location\" : { \"lat\" : 51.0159963, \"lng\" : -0.11289 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.6 km\", \"value\" : 589 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 34 }, \"end_location\" : { \"lat\" : 51.0466553, \"lng\" : -0.1192611 }, \"html_instructions\" : \"Continue onto \\u003cb\\u003eStonehall Ln\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"ibpvHvuUSCUCM@]DKBWLKFOLWTw@|@WX{A|As@n@w@p@o@f@a@VC@e@PkBx@o@VOBMBQBS@]?_@BM@o@Vk@j@\" }, \"start_location\" : { \"lat\" : 51.041812, \"lng\" : -0.1162764 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"2.1 km\", \"value\" : 2079 }, \"duration\" : { \"text\" : \"3 mins\", \"value\" : 156 }, \"end_location\" : { \"lat\" : 51.06123359999999, \"lng\" : -0.1356673 }, \"html_instructions\" : \"Continue onto \\u003cb\\u003eHaywards Heath Rd\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"s`qvHjhVILuCfEIJe@l@_AlA}@|Am@x@W\\\\OLq@d@aBx@}@^iDtAo@R}@XmA`@iBt@YNkBx@cCt@cA^g@VC@SPqGhEc@ZcFtCi@n@o@t@oCrDMN?B@BA@AB?@A@A?C?[bAq@`Cc@lBSv@s@`C[t@u@zBk@jAk@p@oAtAABAB?D?B?B@B?HAHCNe@jAUt@Mp@MfAEj@Kn@ELeAvD\" }, \"start_location\" : { \"lat\" : 51.0466553, \"lng\" : -0.1192611 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"5.0 km\", \"value\" : 4969 }, \"duration\" : { \"text\" : \"5 mins\", \"value\" : 275 }, \"end_location\" : { \"lat\" : 51.10238270000001, \"lng\" : -0.144584 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit onto \\u003cb\\u003eLondon Rd/B2036\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eContinue to follow B2036\\u003c/div\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"u{svH|nY@?@?@??@?@@??@?@@??@?@?@?@?@A??@?@?@A??@A?A??@A?AAA??AA??A?AiDjCIDkA|@a@L]?i@A}B]sBW_@KuCu@q@[eAs@CEOQKS]i@Wo@[}@I_@gAkFIa@a@yAISO]Ue@OWm@s@gAm@}EkB_Ds@_B[EAeC]{@M}@?cBJcC^sARsARa@Fm@F_@@g@@g@Ai@Cs@EYCgIe@A?{CBiE\\\\YDyBP{EZ}@FwBPwATg@PiAXiBh@YTkB`@{HjCiB|@sCdA{@\\\\_Bz@kAt@c@\\\\y@n@mArAgBhCEF}AxBmBxBaCvBiC|AoDnBoE~ByFzCcExBa@XUNQPSNSPe@b@a@TuAt@s@^uCxAOHOHQHOFULWJa@R_@Rq@\\\\_@Ra@Ri@Va@N}@^eA\\\\m@TkA`@o@R]L]JYJ[JMBQDI@kAD\" }, \"start_location\" : { \"lat\" : 51.06123359999999, \"lng\" : -0.1356673 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.5 km\", \"value\" : 536 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 36 }, \"end_location\" : { \"lat\" : 51.1068547, \"lng\" : -0.1451369 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit onto \\u003cb\\u003eBalcombe Rd/B2036\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"{|{vHrf[@N@JANAHCFGHKFG@G?CCCAACCCCIg@d@qAh@KDWHg@N_@JOBmAVQ@[Bi@BkDSmDa@a@Uk@_@\" }, \"start_location\" : { \"lat\" : 51.10238270000001, \"lng\" : -0.144584 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.6 km\", \"value\" : 554 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 39 }, \"end_location\" : { \"lat\" : 51.1079113, \"lng\" : -0.1404194 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e4th\\u003c/b\\u003e exit onto the \\u003cb\\u003eM23\\u003c/b\\u003e ramp to \\u003cb\\u003eLondon\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"yx|vHbj[CJCNAFEFGFIFE@E@C?G?IAAAECIGCCEGCICKCIAI?E?C?G@OBOBG@CDI@ADGBADCB?TE^MVCFCHGNSJSHQH[FUBM@MD_@B_@?c@?Q?UC_@Is@Gc@GYG[GUK[KUISYg@e@m@u@w@q@q@[Ya@u@\" }, \"start_location\" : { \"lat\" : 51.1068547, \"lng\" : -0.1451369 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"17.2 km\", \"value\" : 17243 }, \"duration\" : { \"text\" : \"11 mins\", \"value\" : 654 }, \"end_location\" : { \"lat\" : 51.2583493, \"lng\" : -0.1257921 }, \"html_instructions\" : \"Merge onto \\u003cb\\u003eM23\\u003c/b\\u003e\", \"maneuver\" : \"merge\", \"polyline\" : { \"points\" : \"m_}vHrlZg@]k@c@u@i@qBsAGCiAq@aAg@[Qk@S_@Sk@Uk@U_A]k@S_AW{@Uy@SCAo@Mm@K_@Im@Ko@I]E_@EkAKm@Ec@Ci@Ca@A]Ac@A_@?]?i@@e@?o@B]@a@B]Do@D_@F]Da@F]F_@H]H_@H_@H[Jm@P_@L_@L[L_@N_@N]P]Pm@Z]Pk@\\\\_@V_@VuA`Aw@h@gAx@}@l@eAn@kAn@{@b@c@R{@\\\\wAd@oA^kAZoATmAPmAL_AHm@DiBD{A@aBAiAAMAoBG[AeBE_BIoAI_BK_BKiAMgAI}AQ_BSiBU{AUkCc@aDk@mB_@kBa@{@Q{Cw@aD}@eH{BeA_@gBo@_Bm@qAe@GCs@WkAa@g@Ou@UaBe@yA]aBa@oB]}B_@wAQoBS}AMkBKoAEoAEkBEqACgBE{BGoCMaESqDY_BMeAKa@E}BUwC]sAQeAOeAO{B_@gASsBc@gAYeA[aA[u@Y}Ao@{@a@e@UaAg@kAs@m@_@oAy@_@Y]W{@q@qAeAuAiAk@g@IGq@i@wAeACAcAq@o@c@o@]i@]g@Wi@Y]Qe@UyAo@eAc@YKo@UwAe@{Ac@s@Qy@S{@QyAWgAQ]E[E_AIuAKuAIsBIcAEuEQwDO}AGyCMeBGiBIuBIuBKsAEqAGoAEoAGs@CkDGu@C}CMqAEyDQsNk@kMe@wCMq@CyESiAEm@CmDK_AAk@?o@?_A@kA?_BFw@B_BFaAH{@FeAJe@DmC\\\\u@JwAVgCd@aAT}@TaBb@}Bl@}@Rs@NYFWFuAP[D_AJm@Dk@B}@D_@?Q?_A?uAA]A{BO[CoBWa@Iq@MqAYUGi@O]KoAc@_@Mq@WiGaC_A]_A]m@S_@MwAg@wE{AqAa@{@Wo@S_Cq@qCw@}DcAsBg@}@SqBe@qBc@qBa@oCk@cB]]KoA[a@Mo@So@Se@OmAc@_A_@u@YcD}AqC}AiAq@_Am@i@_@m@a@yAgAiA_AaBuAq@k@}A{Au@u@w@u@mAeAe@c@y@m@YSs@e@oBeA{@a@cAa@[MYKg@Oy@SUG_@Io@Ko@Ii@IUC[A[CyAEy@Am@@_@@_ADu@Dw@JQBUDWDc@Jq@Po@PaAZo@VmHvCI@SJQFkA`@}@Tk@N]Jc@H]H_@F]Fa@Fq@Hq@Hk@Do@FoAD]@q@@k@?qAAaAEoAE]EoBS_@GmBYyB]s@Ii@GMAWAm@Eg@Cg@Ao@?_@?_@?_@@}AHo@D[BSD}AT_@Fm@Nm@N}@VOF]Jm@Vm@Vq@X_@PULa@TSJu@d@u@h@k@^sA~@a@X{AdAqFnDiBhAaCxAqDpB}DtBeAh@c@T]PgAh@iD`BaEdBuBx@oAb@mBp@eAb@yAf@\" }, \"start_location\" : { \"lat\" : 51.1079113, \"lng\" : -0.1404194 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"80.1 km\", \"value\" : 80054 }, \"duration\" : { \"text\" : \"48 mins\", \"value\" : 2850 }, \"end_location\" : { \"lat\" : 51.7149896, \"lng\" : -0.3932109 }, \"html_instructions\" : \"Take the exit onto \\u003cb\\u003eM25\\u003c/b\\u003e toward \\u003cb\\u003eM4/Watford/M1/Sutton/A217\\u003c/b\\u003e\", \"maneuver\" : \"ramp-left\", \"polyline\" : { \"points\" : \"ukzwHdqWOVONc@VQJ]N}@b@y@^m@Zk@\\\\o@^g@ZiAt@gAv@q@j@MNGJsArAg@j@Y\\\\KNMPMNUb@U`@S^MXSd@a@bAADYz@_@hAAFYbAQv@Oh@UdA_@pBIf@ObAGf@Kz@Ih@Iv@Ed@Ed@Cb@C\\\\Eh@MvBCh@Cv@GdAAJUz@KpDQpFElBA`@G~BA\\\\QxHGbBCn@C|@K~BEt@Ev@Ez@InAIjAGv@QtBYvCUpBa@~DUfBg@fDY`BQfAg@nCYzAo@zCy@tD[xAe@fC_@nBW~AOfAOfAS|AMhAO~AMdBGn@K`BATCp@El@EdBAVChAAr@?p@Ah@?^?lA?fA?jA@bB@hABjA@r@F|BBlABr@FvAR|DH`BRxCP|BVtCJhAFn@P|AR`BLhANfAT`B\\\\vBb@lC^vBXxATdAp@bDd@nBn@fCp@bCr@bC~@tCl@hBn@~Ap@bBfAhCnA|CjClG~BpGTr@Vr@z@fCpAjEr@bCr@pCt@xCv@tDh@jCd@lC\\\\tBb@pCd@bD`@tCj@nEXzBXrCRxBTfCPfBd@pGNxBl@rKFxAHjBJrBLpDJpDNlFHfDD`BDhDDnBDlD@|@@fBB~EB~CArB?fC?vAAlCCpBAjBChAEfBCvAChAG~AOfEMvBSdDWtD[hDYvCSjBWjB[|BYrB[lB_@xBYdBWpASfA_@hBUjAS|@a@hBc@jB]tAe@jBm@|Be@`BiBfGs@`CoFlOWp@o@lAMZOb@iJ~VeArCgIrToDtJoClHWt@M\\\\qCpHs@nBgAlC{@pBw@|Ae@|@{@zAs@lAe@r@}@rA}@nAq@x@sA|A_BbBi@h@w@t@[ZkCtB{@n@_@V[Rw@f@{@d@sDfB}At@wAt@cAh@e@XsBrAu@h@gAx@g@b@{@t@iAfAq@r@k@l@y@~@y@~@aAhAeArAiAtAq@~@{@jAcAxAs@fAqArBgAhBwC`FaB~C{@~Ai@jAaBnDw@dBa@|@_@|@w@nBiAvC}@dCw@bCmAxDi@lBcAvD[fA]vAk@`CMh@Qz@Q|@Id@g@fCg@tCi@|Cq@nEe@fDUbBMhAQbBYlC]vDS~BIdASvCQ|BIhAKpAO`COrCM`CIdBOxDMrCI~BGdBIxCKnDElBEhAKzCShEWvDQxBu@xHMhAw@~FYbBy@pEg@|Be@tBe@jBs@hCm@pB_AtCcBpEe@jAiAhCw@`Bm@hAaAfBcBrCcA|AMT[d@iCrD[`@kA|AGHk@r@aAhA{@`AcAhAy@z@eBbB{F`F[VuAbAw@h@k@b@qAz@{BtAe@VmBdAk@XkAj@aAb@k@ZUJMFiAh@{BhA}Ax@{@f@oAx@eAx@YX[Xw@x@iBrBu@`A_@h@IPgBxCYj@Yj@a@v@aA|BoA~C{@fCs@|Bu@hC]nAc@lBk@jCa@~Bc@jCM|@QhAQrAOxAM|AOrBIpAIxAGfBE`BCnAAr@Ar@?^?jA?l@@tA@vADjBJpCL~BNvBP|BRrBRfBPlAJp@PbAd@tCd@|Ch@nDl@xD\\\\jCd@tD`@~D^zDP~BLzABXFx@JxBJhCHfCBzADvAD|EH~HDhGBjBDjDFdDHdDHnCH|BHrBN|CPrC\\\\zFXfEPlBb@tC^fE`@dEb@fDr@dGNvARzABNJt@f@tDj@`Ef@jDp@xDfA|FtAnGp@pCx@nDvAnGhAtFd@lCh@bDb@rC\\\\hCVlB\\\\dCp@fFf@vE^bEVjDVhDLjCJlCHvDBxC?d@AjF?|@?JCvBIfF[hGMpBEd@G|@UxBSzB_@rDeAtJ_AbI]dCYjB]zB[fBADSpA[|Aw@pD]~Ac@dBmA|E{CxLqBtHmApEgBrGAD}AdFqE`PCJwF`RkEbO_@nAQj@GT_CdHsA`EyAtDeC`HqCfHoCxGGPcBjEqAnDGNe@xAcAdDuAtEmApEkAzEoA|FoArGuAjIu@pF_AvH}@jJ[pDUnDc@jGQtDIhBMfCOlDI`BItBKvBKlAQbCS~B[vCGp@CN]rC_@hCUrAWvAa@rBg@|B_@`Bk@xBy@tCm@pB{@fCKXcApCaA|Bu@`BeAtBa@v@]j@[l@y@rAkBvC_ApAi@n@aAjA}@bA]`@u@t@y@v@g@d@y@r@yAfA{@l@YRiAr@}@d@MH{@b@a@PiBx@{@X}@ZoA`@_Bb@g@L_@HcAVs@L{AXq@LaAN}@N[Ds@FeAJiBNeAFeBJqDJiA?aB?_A?oACuAAkAC_@AuAA}B?]?m@Ba@@m@BO?OBI?I@QBYD_@F[D]HUDg@LeAXu@T_A\\\\k@Vo@Zi@Xk@Z]TMHMHEBw@j@eAz@uAnAqArAiBrBoAzAqAzAaAhA{@`Ai@l@q@r@uAxAgG~F[ZcBvAgA|@w@r@i@b@uAjAeA|@eA~@eA~@w@t@mBdBeA`AaB|AeAbAiCdCsAtAWXw@v@cAdAqCzCaBfBcCnC}AdBgBpBY\\\\]b@[b@ED[^KLi@h@u@x@cAbAu@v@[Z[Zw@r@OLMJg@b@]Xa@\\\\EDk@b@]VuAbA{@l@k@^[N]VkAp@m@\\\\{@b@UJWL_@P{Al@o@XkAd@m@Rq@Vm@RuAf@cA\\\\a@Nm@RaGvBaBp@kAf@mAj@]Pm@\\\\WLc@Vy@h@wA`A[ROJk@`@[T[TYTQLIFONwAhAUTkAlAKHs@r@g@d@k@l@e@f@QRGHIJOPWX[`@QRe@l@]d@ILQTKNc@p@ORw@jAiAjBqBhDo@hAg@|@_@p@[j@m@pA}@lBs@~A_@v@i@jAg@fAi@jAq@|ASb@{@lBi@lAs@|As@|AiAfCqArCaAzBu@~Ai@jAO\\\\OX_@x@_@v@Ub@k@hAa@x@Wd@INUb@_@l@CDy@tAo@bAo@`A}@nA{@hAmAzAe@h@s@x@g@h@[Zg@f@MLqAfAqBbBa@\\\\mBtA}B|AgAp@iAn@_B`AwAv@aAh@wBfAgBz@s@\\\\yAn@}At@o@XeCfAOF{@\\\\]PkAf@}B~@gAd@[LSFm@T{@ZYJeBn@_AZa@NUHo@ToC|@m@Ru@R_AZwAf@g@TeBd@oAXcAPw@Jo@Jm@FOBq@Fu@Fo@Di@BI@]@w@@[?aA@g@AsAAmBIM?cAIiAKg@G_AOcAM_BWi@KqDo@eAQgAQ{@KmAQaAKq@G_AKq@Eo@Em@C]ASAc@Ao@A_AAs@?g@?W@a@?oADm@Bw@Ba@BaAFqALoALcDb@o@J_AN{Ch@aAN_ANaBTm@Hg@FUBcAHs@Dq@Bc@@s@@q@?s@?q@Aa@Cq@CSA]Cq@GQAq@IkAQi@Kq@KICq@O_AWaAY_@Ks@UOG]OYKUIWM]OWMa@S]Qi@YUO]Sm@_@_@Um@a@k@c@m@c@i@e@]Yi@g@y@w@i@g@OQu@u@QSaAeAg@m@cAmA_AqA_AsAiAeBEGoAiBkA_Bu@_AyAgBACg@e@k@g@k@c@k@a@a@W]Uo@]o@[k@Yi@Uk@Ui@Su@WqAe@oAc@o@Uo@W_@Oq@Yi@Wg@W_@Qm@_@a@U]U]UYSc@a@[[QQWU[Yi@g@i@i@eAmAi@o@[a@i@q@g@s@U]Yc@gAgBACYc@Wc@Ye@We@SYEGYe@Ya@W_@Ya@e@q@[_@W][[[_@Y[]_@w@s@k@i@QMk@i@o@g@YUm@c@k@a@o@_@m@_@m@[_@S]Oq@Yo@Wo@Uq@UaCq@aAYqBk@aBg@_A[UGm@Q_Cq@sA_@_A[y@Y[Mo@Wa@Qi@Wg@Wk@[}@g@_@Wk@]cEuCkCqCuDgEgEgG{BiDcBmCgCyDs@aAmBaCmAsAaAeA}@_AcAaAqAiAqCaCcAu@eBmA_BcA{@e@aB_A}@c@m@[{@_@_Aa@oAe@_A]o@Uq@Sm@Q_AWaAU_@Ks@Qg@Ky@Om@KYGg@GYEy@GY?uH_AeAOaAMuAUcASeAWg@O}Ac@c@QaA]iAe@w@][Om@]}@g@m@a@gAs@m@c@_As@}@u@y@s@s@q@s@q@u@w@c@c@y@}@_AcA[]aAaA}@_Ae@e@mAkAeA_AeA}@eAy@yByAaAm@{@e@m@c@_@Ui@[q@]gAe@w@YyAe@kA]u@Qo@OgASq@Km@Kk@Ig@EaAK{@Gq@EkAG}@AwB?qAB_BDq@DeAH]BYB_@Dg@HqARmARcATs@P{@Tg@Lw@Vq@To@V_A^sAl@w@`@q@^A@qAz@aAl@}AfAoA`AoAdAq@l@mAfAg@d@iAdAe@b@e@^eA~@k@d@c@\\\\[Ty@j@kAv@mAn@}A|@{@\\\\qAb@q@T{@Te@JYFs@N[D[Ds@Jo@DqAJq@BoBDsAAaBI_AGgAM{@Mg@Ko@Ou@Qk@Qg@Ke@Me@Qo@Ug@Um@Ym@WsDkBy@a@kAi@yBu@}@Y_AYmA]o@O}@QoAUcAO{@Mq@Gq@GuBOaBI{BIaBGmACoBEmBA}B?_A?y@?eBBmDH}FVaCJeCPmBPoBR}Fr@aANmC`@oB\\\\kE|@oCl@}D~@{@VmA\\\\}Ab@_Bf@wCbA{DvAm@TiBr@}@b@gB~@y@h@gAv@MJ[V[X[Zu@t@i@n@i@n@WZW`@m@|@gAdB{AtCUb@_@v@g@hA_@~@e@pAUr@Ur@Y~@Qj@Ol@Ol@Ml@Ml@UfAQ`AQhAU~AU~AM|@Kx@Y|B_@pCQjAKr@Mv@Kj@O`AYvAMn@[|A[xAg@rB_@xAa@xAa@tAY|@a@lA_A`Co@dB_@|@IP_AxBa@z@_@r@aAlBy@tAYf@Wb@W`@[d@W^Y`@o@|@a@f@]`@c@j@k@p@YXcBbBGDe@`@]ZEDSPeAz@sBzAsAz@WPoAv@]TuA|@EB[RqF|CoBdA{Av@uBdA}@f@mAf@{CtAwAl@cCbAc@NcA`@}Bz@}@\\\\aBj@w@VuAd@uBn@_Cp@aEjA_Cj@eB`@sGxAs@Hw@NkCd@e@H}ATmDh@mDf@kANw@H}AN_BLkAHaBHcADq@BaA@_A@aAAoA?m@Ca@Aa@A_BIuAKqAMqAO{ASoCe@cAUuA]k@Os@SiA]oCw@iDw@aAS{@Kq@ImAKq@EoAEmFOoFUoCQ_DUoE_@aBQwBWuASiAOwAUsASuAWmBYcASw@O_AS_BY{@QqFqAg@MMEaAUaAUkCs@aCk@{Cq@mDy@uD{@yGyAmEy@{Ba@aDk@yDu@{FgA{Do@kEaA_AOcAOg@I_BQ_BQo@I_BS{Dk@m@KmDg@{Ew@_C[}@K_Go@cGs@sFe@mBWmB]oA[iAYkCw@{Ak@_A_@yAo@}A{@gAo@gAo@}AaAwB{AiA{@u@o@c@_@SQk@e@i@g@u@s@yByB}A_B}AaBmBmBmEoE{CcD_AcAwA}Ak@m@qAwA_DkDeAqAmA{AwBmCyAkB}@kA{@oAe@q@uAuBqAuB{AsCa@u@m@mAg@eAy@kBgAcCo@uAi@iAa@}@S_@_AmBi@cA[g@iAiBs@aAc@m@UWg@o@u@{@u@y@y@y@w@u@_BsA}AmAm@a@k@_@gAq@o@_@sAq@sAm@oBw@}@[y@WmCu@}Bm@eCy@a@OYK[KSKUG[IYK_@M{@UyA]cAW_AU}@[o@Wm@WQMa@Wu@e@oAeAqAiAw@u@iCyBw@o@i@_@_BaAaAe@]MkAc@k@QOE{@U}@Qo@Gc@Eq@Ag@Aa@?wABgCH}AFqDNwCDeDDgAA{@EcBOoAQq@Ma@KqA]q@SyAi@}@a@_B{@e@Yy@i@c@]e@_@mAgAk@k@o@s@k@q@q@{@i@q@c@q@c@q@[i@]m@m@kA_AoB_A}B_@gAYu@q@yBW_A_@yAc@oBUgASkA[eB_@kCQsAUqBMsAKuAKuBMyBWmFWiFm@sM]mIWyHOuDIuBIgBQyDSoDIqAO_BGq@Iq@MiA]_Co@wD]_Bs@{CYgAc@uAg@}AcAiCkAoCo@uAo@qAgAcCgAgCaBoDwBwEIO{CsHyAeDgAaCaAsBgAcCs@_By@aBWc@c@y@c@u@wA{BYa@o@y@m@u@UYe@s@[]g@g@k@g@s@m@w@o@m@c@m@_@s@_@k@Wc@Uq@Yk@Uc@Qm@Si@Ok@Og@MkAU{@M_AI{@Ge@CmACgBCsCAwBCyA?w@?iACeBCiDCkACmAGsBMiBS}@KWEkAQo@OgB]_Bc@gA[mAa@kBs@{Aq@yAw@mBgAiCeBgA_A}AsAy@u@iBkBkAqAk@s@_@e@iA}Au@eAgAaBo@aA]g@m@_Ak@_AgAsB}@mBYk@Wi@q@uAoAwCgAcCw@}Aw@yAy@wAgAmB{@qAs@cAq@aAg@o@mAaBo@}@s@eAy@uA]m@KQ}@_Bm@oAw@_Bw@gB]{@c@gA[y@]aAe@wAO[[a@[eAQm@Su@Ss@Ok@YmA_@aBYoAYuAWmAUmAQcASqAQgAO_AKw@Iq@Is@Im@Iy@I{@Iy@KoAIeAIkAIsAIsAGkAEcAEmACu@CkAAw@CuAAkAAkA?yB?iAJy`@?uA@qA?oA@qC?_B?kB@{C?uD@kD?_E?gEAeCCqBAm@EuACmAGuAEcAG_ACi@Gu@KuAIcAI_AIw@Iy@SmBu@yG\" }, \"start_location\" : { \"lat\" : 51.2583493, \"lng\" : -0.1257921 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"91.3 km\", \"value\" : 91260 }, \"duration\" : { \"text\" : \"55 mins\", \"value\" : 3295 }, \"end_location\" : { \"lat\" : 52.32487159999999, \"lng\" : -1.1410307 }, \"html_instructions\" : \"At junction \\u003cb\\u003e21\\u003c/b\\u003e, exit onto \\u003cb\\u003eM1\\u003c/b\\u003e toward \\u003cb\\u003eLuton Airport\\u003c/b\\u003e\", \"maneuver\" : \"ramp-left\", \"polyline\" : { \"points\" : \"uqszHpxkA_@}@a@uB_@gB]wBQeAUsAMy@_@qBUmAYiAWw@[u@_@q@Y_@UYYYOKWQUKYM]KQCYC[CeACe@@e@?_ADm@@]@q@O_ATe@Li@PWHcA\\\\k@PIBw@Ri@NgBXMByCp@wA^q@Ry@Em@TkCx@GBcA\\\\cA\\\\kBl@OD}Bt@}FdBaAVmBf@YH_Cn@}A`@aFpA{Ct@WFWFqAZiB`@sBf@{@Ts@PkA\\\\m@Ry@XaA\\\\cAb@y@^s@\\\\gAj@q@`@s@`@w@d@u@h@{@l@m@d@o@h@aAx@gA~@mAjAcAfAeAjAm@p@a@f@}AnBaAtAcCrDyAzB}AdCaAvAiAhBs@jAw@pAy@xAU`@KRc@x@aAlBuApCEFgBhDqA`Cy@xAw@tAy@vA_C~D_BhC}AjC}@zAaA|AuAtByArBsBnC{@bA_AbAmAnAs@r@w@p@iA~@gAx@_An@eAp@_Ah@s@^s@\\\\s@Zy@\\\\mAb@q@Tw@T}@ToAXq@Lo@JgANiALaBJgAByABq@?s@AcACs@CeAIsAMqAQiB[eAUc@KcCm@oBa@[G[IOCKCyB_@}AYsAWoAUg@MmB]yCk@gDu@m@KYGIAiASkCg@}Bc@_Cc@iB]sCi@oKsBmDw@uE}@iFcAmGiAkEq@yDa@sHg@iFQmDCsB?wD@}DH]@I@wBJq@D{CPwD`@}ARg@FC?k@HgAJgAPaGz@k@FgI~@eI~@qAN}Dd@gBVoEj@mEh@cD^aD\\\\_ANs@J}Fp@gH~@qFt@q@HqCZUDo@HsDb@cD`@cBPeI~@}Fj@WBkFf@wEd@a@BcCTw@FYByDb@{Fd@kBRs@FC@M@i@FoAJwALqGp@eD\\\\oCVKBgEViAJkBP}Fn@eGj@gFf@wCXeDZcD\\\\oEd@gE^YDq@Fy@H_AHa@DkE`@sD\\\\_Ir@sNtAsBRaP~AmIz@e@DyCX_CLo@FcL`Am@FOB]Fs@HiB`@yA\\\\mA`@kAb@iAd@k@VwAr@iAn@iAr@cBjAeAx@cAz@eA`AsAtAgAnAkAxA}BxCY^Y^mAdBu@dAq@|@uArB_BzBs@`A}@nA}@nAMPo@|@c@l@s@`As@|@yAfBu@z@u@x@q@r@{@v@uAlA{AnAcBtAiBtAuB~AwBdBq@h@q@h@uBbBwAlAiB`BuBlBwCrC_A~@oAnAeBhBkBrBoD`EiBvB}AlBmAzAiB~BgBbCaArAo@|@w@fA{@nAkAfBs@dAe@r@q@fAo@bAo@bAq@fAs@lAq@jAu@nAs@nAm@dAq@lAu@vAs@pAi@dAw@|Aw@|AiA~BiBtDaAtBaAtBw@`Bs@xAq@zAs@~Ag@fA{@lBi@hAi@jA_@z@a@~@k@nA_@x@u@fB}@lBgAhCmAnCg@lA_@z@mAhCi@bAc@x@_@n@u@jAk@t@a@h@a@d@k@l@a@`@k@f@g@^]V[VULMJWN]PQHGDw@\\\\s@Xe@Ni@N[Hu@Ny@Js@HoALiAHoBPmBNmFh@qBRsARuAZ}Ab@k@Ta@NoAl@oAv@KF}@p@gA|@s@r@o@p@QRc@f@s@|@oBbCw@`AwBhCqA`BkB~BgC`De@l@mBlCyAjBc@b@y@z@wApAMLWRgCzBiA~@}AjAm@`@y@j@{@f@OJOLuCrBmAv@uA~@oD`CcErCmAz@sBvAmCpBoAz@_FbDiAv@k@^gBjAuBxAmBnAkBpA{@j@eBjAiFnDeC`BsBvAsBvAaBfAq@d@mEvCeBlAaAn@}AdAsDxBuCzA_Bv@mBx@uBx@gBn@oBn@}A`@oBf@mAX}AZ}Cd@cCZmBReCNgDLwBDyFJyAByAHk@DsCT_@DWBwAR_ANaDh@_B\\\\wA\\\\eAXi@Nk@Pc@Lc@NoAb@q@Tm@TSHgBt@gBx@gB|@MHyBlAq@`@_Aj@s@f@cAr@}@n@q@f@m@d@UPm@f@i@b@[XqAhAk@h@a@^i@h@i@h@cAdAa@b@cBlB{@bAk@r@m@r@g@n@A@gAxA]d@k@x@i@t@g@v@w@lAe@t@gAhBcAfBeAlBc@x@a@t@kA~Bc@|@i@hAQZe@bAUd@k@jAUd@_@v@mBtD_@n@aAdBk@`AGLOVU\\\\Yd@e@r@_@h@iEfGa@j@c@h@c@f@c@h@qAvAk@n@a@^kApAYZURaAbASRc@`@{@v@kAdAaBtA}AjAkA|@iAv@eC~Ag@XyBnAgCpAuB`Au@\\\\iBt@oBr@oAb@oA^oCt@{A\\\\kB^}@PcARw@JYDkBVqBTqBPgBJyCHeA@kB@y@?uCGaAAq@Co@AU?k@AyABU?mAF_AFqANoARgATWDo@NQD}@R]JkA^ODu@Vs@ZOF{@`@c@Tu@^k@\\\\k@\\\\YR{@l@OJk@`@m@f@e@\\\\c@\\\\{AlAgA~@mA~@c@\\\\]VsB~AcBtAy@p@wBdBkAdAm@f@s@h@IFKFkCrBy@p@[XqBzAw@n@s@l@w@n@OJiAz@w@j@s@h@a@Zi@`@MLq@f@c@^gH~FkDrCq@j@i@b@i@b@e@^m@f@sDtCgA|@w@n@{AjAWTeF|DoElDgDhCuAhA_Ap@q@j@aAt@q@h@gA|@{ChCcBvAmAdAaExDcC`CoApAk@p@Y\\\\Y\\\\eBpBaC~CgB~B_@f@eA|AcBhCuAvBQZs@lAy@zAa@t@m@fAm@hA_AlBm@lAo@rA]v@Wd@g@hAi@jA_@z@]v@yArDi@tAo@fB[~@]~@[`AqAbE[bA{@vCY~@[dAOl@eAzD[nAWbA_@zA{@xDUdAg@bC]dBShASdAk@fD]tBW~AQfAQhAYfBm@pDQhA]tBW`BQdAMv@O~@QfAiAjHcCtO_BbK{Gxb@kArHw@`Fy@~EaBpKiAbHq@jEe@rCMv@Mt@]bCy@`FiBfLcAnGEZMz@aBdKsBtMKj@G\\\\u@|EeBxKgE`XoGda@mC`QiDdT_BbKy@lFgF~[]xBk@fDcAlF[|ASfA{@vDYrAa@dB}@zDk@xB[lA_@zAk@pBo@~Bw@fC{CjJsBxFeCdHy@~Bo@dBmAdDiBdFaBrE{BnGsBvFsB|FmAfD{BnGkBdFkAfDcFjNs@pBY~@[|@o@jBk@hBm@jB}@xCi@jBk@nBi@nBW`AW`Ag@pBo@fCeAlEiAdFw@pDuBzJwB|JuC|MuApGcAtEaBxHaAnEoDnP{E|TcAtEm@nCYtA{ApG}AbGw@rCk@nBs@`Cy@hCu@xB_ApCi@fB{@hCiAfDcA`D}BbHYv@kApDeBhFADeCvHoBbGmB~Fk@bB_ApCcBhFqA|De@vAaBrEuCrH_A|BoCpG}BpF{BhFw@jBq@|AKTeBhEaAvBoAzC{AnD_BxDoAtC}AhDoA|CuA`D]v@sGnOwCbHuDtIc@|@]r@aCbFw@bBeDzGy@dBoAjCcArBgBrDkBzDa@z@c@z@aDvGCHuCbGw@|Aa@r@Ub@cAjBcA|AaAxAm@z@CDc@h@{@hAaCpCiAfAw@v@sExDk@f@cEjDuIlH}BnB}@t@oB~A_BrAaAx@mAdAgA`AwDpDqBvBgBpBmAvAsBhC}ArBiBfCcClDmDzF}@zAoA|BqCnFoBbEoAnCeDbImCfHcApC}CzHgB~EABYv@[|@e@tA]|@o@dBo@dBw@vBy@xBq@fBg@rA]~@q@fBg@rAg@rA]|@e@rAg@rAg@rAg@rAq@hBq@fBo@fB{@|B{@~Bs@hBo@fByA|DeArCkBbFaCnGuAvDaBnE_CjGmB`F{BnFqA|CmCxFyBnEeC|EuBtD{ExI{Ql\\\\}NzW{Vdd@sItO_BpCqBrD}@`Bm@dAa@v@aBtCqA|BgAjBeAdBu@hA{A|BcAxASXaArA{ArBW\\\\w@bAwAdBkAtA]`@_BdBe@j@mDlD{@x@w@t@oE`EgA~@aIfHeA~@eA~@SN}AvAoBdBi@f@_BvAsAlA[Vo@j@kAfAmBfBuDdDu@p@gDxCi@d@w@r@q@l@m@j@[Xo@j@}@v@gA`AoAhAw@r@sAlAgA~@g@d@{BpBeA`Ay@t@]Z{AtAqAnAw@r@KLURuAtAuBrBa@b@iBjB}A`B{@~@mEzEe@h@}AfBaAfAyAfBmAxAy@`AgAtAkAxA_AlAq@z@_@f@wBrCw@bA}@nA{@jA{@hA[d@a@h@[d@w@dAk@z@qAjBo@`A]h@m@~@mAnB{@rAgAdBc@p@k@~@iAlBgAfB_@p@e@v@y@vAo@dAa@t@i@~@]l@cB|CqBrD}DxHcBfDaAlB_CpEaCbFCFsE~JcOh\\\\m@rAm@tAw]vv@_GrMk@nAkFnLqD~HgB|DyIvRuCnGkAdCeCbF}@dB}AtCA@oBlDmCpEaCbE_BlCaCbEsBlDaBnC_BlCi@`AaBpCcBrCuBpDeAdBu@lAq@lA_FnIyB|DuAlCs@tAyBrEkAjC_AxBiApC}A|DsBtFyBhGs@xBqC|ImAdE}ArFwArFg@rBcB`HqA`GqAlGq@hD_@jBY~AY|AQ~@EVw@|Em@zDgBfMw@fFg@hD]vBe@jCq@vDkAlGq@bDa@jBaBnHCJu@|C{@nDmBdH}@`Ds@|BcAhDwB|GiDlK{EbOaE`MuDdLq@tBk@hBwAtEg@fBqAtEeAzDu@vCk@~B_@zAa@dBYrA_@~Ac@rB]`B[vAI`@s@tDs@tDo@pDq@~Dm@zDYfBq@zEaApHwAnKmBdO}Kh{@mJdt@OhAmAfJkA`JuAnKkBpN_CxQaB`MeAvHa@jCy@rF[pBCNc@rCo@|Dc@dC}@dFShAk@xCs@xDo@`Dg@dCm@vCk@lC[vAu@bD]~Am@jCy@hD{@jD{@fDs@nCk@vBmBfH]pAW|@}@tC_BjFgBzFcDtJ{CtIsAxDeC~GmBrF{BjGuAvDcAtCw@vBoAnDo@hBo@fBg@tAqC|H_AhCoCzHcBzE{BjGmBhFgBvEeAnC}@|BwEbLy@jBo@zAi@lA{FbMk@lAyExJ_HtMuBvD{B~DeCdE{CfF_AxAiBxCkAjB{BhD{BhD{B`DgBfCcBrBiArAeAhAcA`As@p@}@r@u@l@oA`AgAr@}@j@wAt@cAj@u@\\\\w@\\\\_A\\\\kA`@_AX{@VkAXoATmAT{@L}@HmANqADoABmABsDDcAFmBLeAHkAPyAXu@LgAXsBl@y@XeBv@{Ar@eAl@yAz@s@d@g@^q@f@s@l@}AvAwFrFmCdCiFdFkCfCkEfEaA~@i@h@k@f@aF~EeD`DmDbDwFpFeG~F}EvEqDjDaE~DiF`FkDfDuBpBoFlFeB~AeEzDcDvCmDzCgDtCsDxCuErD{BdBgGpE_CbB}AdAeD~B}B~AmDpCyBdBgCtBaBtAkB~AuEhEgA`AaC`CqDtD{@~@o@p@}@`A{@bAmFhGUVSVk@p@_CvCsD|EqE~FgAxAaFnGgD~D{BjCsBzB_BfBsBzBkBlBcDbDkAfA\" }, \"start_location\" : { \"lat\" : 51.7149896, \"lng\" : -0.3932109 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"69.8 km\", \"value\" : 69837 }, \"duration\" : { \"text\" : \"39 mins\", \"value\" : 2367 }, \"end_location\" : { \"lat\" : 52.9112713, \"lng\" : -1.3000362 }, \"html_instructions\" : \"Keep \\u003cb\\u003eright\\u003c/b\\u003e at the fork to stay on \\u003cb\\u003eM1\\u003c/b\\u003e\", \"maneuver\" : \"fork-right\", \"polyline\" : { \"points\" : \"muj~Hlz}E_A|@wBnB}BpBmAbAoAbAyEzDgF|D{CpB{AdAaEfC{EnCeB|@qJtE_DtA}GrCuK~DyGdCsExAuEvAiF|AsA`@eDx@oAVcDr@_ARkF|@uGx@w@J_BH_@D_@B_CPYBWBkGj@cFj@}C^mC^yATsB^kATo@LmB^}Bf@}Bj@wBh@aCp@mBj@qBl@iBl@kBn@yBt@{Bx@wDrA}CfAgDjA{CfAiC|@yCdA{Bv@qC`AcDjAyCbAiC`A{Bv@kC~@iC|@kBp@{Bv@aCx@eC|@gC|@iC|@gC~@}B~@wCnAgBz@yAv@iBbAiBfAkBjAaB`AaB`A{BjAmAj@wBbAkBv@}Al@yCdAyBr@eBd@cCn@}A`@iBd@wA^MDWFUF_Bb@}Bj@uBh@yBj@mBf@gBd@sA^iBb@kCr@uBh@eFrAe@LaAV_HdBmD~@u@RuC^mBZuAZiA^sA\\\\}Af@sAd@gA\\\\cC~@{CpA}@`@}@^qAp@gEdCyDhC}BrAuAx@}A~@m@b@QJm@^_@Xu@f@q@d@_Ar@{@l@w@l@k@d@g@^k@d@g@`@iA~@aAv@gAbAiB~AiC|Bw@r@i@f@c@Zq@h@gA~@kAz@q@f@mA~@wAbAgAt@gAt@c@Xg@\\\\aAl@q@`@{A~@kBdA{BlAwAr@yBfAcBx@iChAwAj@yAj@EBoBt@s@VcA\\\\iA`@aAXwBn@gBf@iBd@eB`@MBgAVA?_AR]FmB`@iBXcC`@eC\\\\s@FaANsD\\\\_@DSB{@Fm@DoBJcBHoCJ{@BcABeA@qA?mABcBAaAAq@A_@?eCGgCI}@EI?s@Ei@Em@C_@Cm@GuAKWCe@Em@Eq@Io@GaAMaAM_AMqASq@Io@Mq@MIAe@Io@Ma@I[GYCKAsA[_ASs@So@Q{@Sq@Oy@QeAScAOm@GcAKcAKw@EWC_@Cc@?O?c@Ck@Aq@A_@@o@?y@BcADcAFo@Fs@FcAJq@HaAP[Fy@PwA^w@Ta@L}@Tu@R}@Rc@JK@eARsATu@Jo@JcALeAN_AHsALc@B_AFcAFeADqADcABiA@kA?cAAq@AcACeAEq@EsAIaAGs@GeAIaAGsAMo@EgBMuBOeE]{CMuBGkCCmPQiFGgCCsAAuACaJKq@?eCEuA?gACsAAsAAaACcBAcAAeBCsACs@?wDEW?I?eGIeAAeBCw@Ai@?i@AiAAg@As@A[?q@Ai@?u@AiACo@Am@Ao@A_@Aa@AYAi@Cc@CkAGk@Eq@Ey@Ik@GmAMq@IQCqDg@k@K_@G]Eg@Iw@Km@Kq@KaAOe@G[Gu@Ks@Ke@G_@GUEi@Im@Im@Ii@Ic@IYEWEUE]G_@GWEUE[E_@G]GWCk@Ky@Mu@Ko@Kw@Me@Im@Kq@Mw@Q_@Km@Oi@Oc@Ka@Oc@Mm@U_@M]M]Og@Sa@Oi@W_Ac@y@a@cAi@KE_BaA{AgAgGiEYSq@e@e@]g@]s@g@q@e@o@e@k@a@s@g@y@m@k@_@w@k@k@a@e@[k@a@i@a@g@[]Wk@a@_@Ws@i@m@c@eAu@s@g@q@c@wCmBa@WYQsA}@aBcA{A}@gAs@kAs@cDkBkAq@cDiB]S]SiAk@_Ae@_Ac@_Ac@}@]_A]}Ai@cAY_AYiAW_AUoAU_AO}@OmAOqAMwAKgBIoACmACqA?yA@}@BcADc@@kAJoCTmBVy@NoATeDt@}DlAaDlAgFbCeDdB_@P}GjDsJfF_Bx@kCpAsAp@uBfAcAf@{@b@C@o@ZeBz@wAt@g@Vm@\\\\i@Vs@^iAl@m@Z}@d@eAf@sAl@cA`@cBn@aBh@{Ab@uAZyAZs@Ls@LkANuANc@De@DcAF}@Fg@@_ADs@?wA?mAAu@Ao@EcBI}BS_AMcBUgBU}AQuAK_BKwBCgC@}ABq@DqAJ_AHmANy@LoARk@Lm@Ng@JqA\\\\q@Re@PeA^_A\\\\qCrAkB`AcFtCuBjA{R~Ke@Xe@VaH|DkPhJmNbISLSLkBdAu@b@]PwBlAw@b@yAv@_@P_A`@q@Xa@Pw@XiA^_AXuBh@aARuAVaALg@Hm@Fe@DgAHu@BiABy@Bc@@gA?c@Aq@Aw@C}@EaAG]C_@Eo@G_@Ek@IeAMy@Q_@Io@Om@My@SkFsAcAWmA[}@UiA[sBg@i@O}Cw@eBc@kBc@kDu@y@Mw@MsAS{ASeAKuAOeBMg@COAwAGcCIoBC_DAgDDiDL_AFc@Bc@D{CV]D_@BeBVmBXaBZkB\\\\qAXeBb@mA\\\\{Bp@WHYHeC`AWHi@RaBr@}BbAa@R{CzAgBbAeCxAsAz@qChBsCfBuA~@wCpB}@j@iAt@{@j@g@\\\\mAv@gAr@[R_@Vi@\\\\{@h@m@`@eAr@uBtAyHbFoChBuBrAwBtAuBrAcC|A}AdAwDbCw@f@_@TgBlAaC~AoD`CgBjAuCnBiC`BcC|Ag@\\\\o@`@o@b@gLrHqH|Eg@\\\\OJgIjFk@^u@f@aAv@cAv@k@d@k@f@uAnAgDbDoFrFgInI{@|@yC|CcCdCsDxDuB~B_@b@yA`BmAvAyBpCcAtAeAtAW^Y\\\\_ArAaChDkBrCmBzCcBpCg@z@gBxC{AnCkCbFoB|Dy@dBMXq@vAkAfCcB|DcBdEmB|E_AfCMXuAxDi@xAi@|A]bAgAdD}@rC}@|Cu@lCo@lC}@hD{@bD{@`DcAfEu@bDy@nDy@vDs@bD_AdEcAnE}AjGsA`FoA|EqA`FeAtDeAvD_A~CcAhDw@dCe@zAEJu@bC_B|EoAnDi@zAc@nA_CnG{AdEgBrEYr@qB`FiAnCa@~@]v@gB~D}AhDoAhCq@tAiBdDk@bAaBlCkAjByAtBkA|AaApAoAzAeAjAq@r@{CzCs@p@o@j@_@\\\\_@\\\\mB|Ao@f@o@`@yAbAiBhA{BnAgBbAe@T]PkBx@gBt@mA`@aAXoA^qEhAeCb@kBViCXi@Du@Ds@Dg@Bm@D{@B}@B}@@kCBmCHoAFoAJqAJuANiAPmAPmAT{Cr@wDhAC@w@V{@ZA@kAb@{@ZmA`@w@V}@VaAV}@Ru@Ru@Lu@Nq@Js@J_ALm@HaAJy@HuAHw@D_EJ{@?{AAyAAmAEcV}@gAEmAEsBImAGy@Ce@CsAGuBG_BG}BK{AGaAEmAG_BM{@Kq@KcAOsAWoAYk@O}@W_@Ki@Qm@Sk@Uq@WeAe@q@[u@_@g@Y}AcAaBeAgAy@uAgAkB{AgAeAsAuAiBeB}AwAc@c@g@c@oByAuAcA{AeAkBiAaB}@yAs@iAi@A?eAc@s@Y_Bk@qA_@sBm@_ASkB_@gAQaBU{AOiAI{AI}BIgC?eBBaCHeCPeBJqBFcBBcBBqB?iBEeBGuAKqBQk@G_AM[E]Ga@GQEWCYG}A[sA]y@S}@WwAe@oAc@u@WuAi@eBw@sAo@oBeAkCwA}BiAiEsBoD}AkCcAuCiAwCcAcBk@}Ag@gBg@{@WcAYs@QkCq@aCi@cASaB]eB[cAQgBYwBYiAOeAMmD_@aHk@}@EoDQw@CeAGcACkAAqAE{AEoA?q@?cA?}A@iA@_@@_AFqADu@BiAD{AH}@DuAHaBLYBgAHq@F_@BkDb@o@H}@JcC^MBmBX}B`@cAP{@R_APy@P_ATc@JYFYFe@LmBf@qBj@_Bd@m@Ru@TeBl@sAf@yAh@yAj@mBt@aBp@qBx@mAj@gBx@_CfAs@Zo@\\\\gAj@y@`@y@b@_Bx@}Ax@sAt@sC~AsBlAg@XOJGBEDs@`@yExCkCdBkAt@gH~EQN_Ap@mDhCyAhASP{AjAqC|BqAdA{ApAiCvBkGvFeGtFmBjBeHhHc@f@i@f@g@j@qLxMqIdKmCfDwBfC{B~BuCrCsDdDuCxBgBlAgC~AmBdAaAh@cBx@eEfByAl@_Bb@]JsEjAYF[Fg@L{B^_CZeAL{BTe@BwAFG@iCFoA?oA?o@?_DGmAGsDWkDc@gCa@}EeAaD_A]MqAa@eBq@sCmA{DaBuQ_IeCeAeDuAyDcBoEkByCoAoJaEqCmAcGgCqDuA}DoA{Bm@i@Mu@SiDs@iB]sBYcBUm@G{@KoAKo@GiAGeAGcAEeBGqCCcBAiBBu@@m@Bo@@u@Bm@Dq@Dw@Fu@Fw@FaAJu@HcANcALeAPcAP_APeAR_B^cARaCp@eDdAiDlAaDpAuB|@oBz@eCbAoClAwGpCqClAwEnB}EpBoBx@a@Na@PuLhFsBz@cGfCeEhBsH`DmBr@wB|@aKfEa@Pc@RwB|@kBv@uB|@yCrA{An@{An@}@^iAf@{Ah@oA^kBf@qAVyARmAN}ANmADoAB}A?oACoAEmAImAMmAQ}@QmAU_AUoA]_A[mAe@}@]m@W[O{@c@iAm@_B_Aa@Wa@YmBuAqBaBeA}@sAqAyB{BaBiBi@k@i@m@g@o@kBwBuAuAqA_BwB_CuBwB{@y@cBwA_CeBeBiA{Ay@eB{@{Ao@}Ao@kBm@oAYaB_@oB]aBO_BMqAIaBE{A?_BDaAD_BJoAL_AL_BZkAR}A`@m@Rk@PqA`@{Ad@aDv@qB`@}Cd@g@Fk@DuCPgABu@AoCKmBKwC[UE{Ca@_AQ\" }, \"start_location\" : { \"lat\" : 52.32487159999999, \"lng\" : -1.1410307 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.4 km\", \"value\" : 426 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 28 }, \"end_location\" : { \"lat\" : 52.9150088, \"lng\" : -1.3006098 }, \"html_instructions\" : \"At junction \\u003cb\\u003e25\\u003c/b\\u003e, take the \\u003cb\\u003eA52\\u003c/b\\u003e exit to \\u003cb\\u003eNottingham/Derby/Ilkeston\\u003c/b\\u003e\", \"maneuver\" : \"ramp-left\", \"polyline\" : { \"points\" : \"mf}aIf||FKJA@CBA?A?C@K?k@EkAAK?yAAaB@qGH_@HE@IDIBGDOLMRS\\\\\" }, \"start_location\" : { \"lat\" : 52.9112713, \"lng\" : -1.3000362 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.7 km\", \"value\" : 703 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 50 }, \"end_location\" : { \"lat\" : 52.9176608, \"lng\" : -1.2936819 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e4th\\u003c/b\\u003e exit onto the \\u003cb\\u003eA52\\u003c/b\\u003e ramp to \\u003cb\\u003eNottingham/llkeston\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"y}}aIx_}FARATARId@GRKPKPQNSLODO?QCMEUKS[IOKUOg@[sAa@{BUmAWoAG_@C]CUAWFs@?AAkBAc@SyAUyA]yB[{BE_@G_@g@wCMo@AMAME{@\" }, \"start_location\" : { \"lat\" : 52.9150088, \"lng\" : -1.3006098 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"1.9 km\", \"value\" : 1914 }, \"duration\" : { \"text\" : \"2 mins\", \"value\" : 94 }, \"end_location\" : { \"lat\" : 52.9202825, \"lng\" : -1.265676 }, \"html_instructions\" : \"Merge onto \\u003cb\\u003eBrian Clough Way/A52\\u003c/b\\u003e\", \"maneuver\" : \"merge\", \"polyline\" : { \"points\" : \"kn~aInt{Fq@kDa@mBUkASeACMa@gCU{AM_AKw@K{@Gm@Gc@Es@CUKoAEw@GcACk@Ey@EcAE}ACeAE{AE_BEoBYsM?G?QWsKAq@A[As@Ck@AUQ{HCyAEmBIwCGqCIeEKsEIaDGcDIgDEmBEgBIgEGqCEiAAo@?UEmBA_@Ag@?a@@W\" }, \"start_location\" : { \"lat\" : 52.9176608, \"lng\" : -1.2936819 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"4.4 km\", \"value\" : 4365 }, \"duration\" : { \"text\" : \"5 mins\", \"value\" : 309 }, \"end_location\" : { \"lat\" : 52.93899949999999, \"lng\" : -1.2168652 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit and stay on \\u003cb\\u003eBrian Clough Way/A52\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eContinue to follow A52\\u003c/div\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"w~~aInevFC?CACACACACACCCCCCCECEAGCEAGAGAG?EAC?C?E?C?E?C?CQk@O[[_@]a@m@w@k@q@]a@]c@SU{AiBw@y@cAeAcAcA{@u@gAaAmA_As@i@kDcCwAaAm@_@c@[e@[e@Ya@[y@k@{@o@iA}@i@c@i@g@i@e@[YY[w@u@u@y@eAiAw@_Ac@e@[c@g@o@_AoAkA}Aw@gAcBsBoB{BmAqAcB_Bi@e@cA{@s@k@gBwAe@_@i@a@u@e@_@Ya@SQEa@IOGKIGCGGIMKUEKK]AIOoAGw@?KEa@C[Ge@CWKu@Io@Ga@Ko@Gc@Ke@Ia@Ic@Mk@Mm@YmAIc@S{@Kq@Kg@Gi@Ei@E_@Ca@Ek@G_AEo@G_AGeAEk@MuBC[GkAMiBC}@EqAAy@?aA@mABcAFyAF}@Dk@FmAN{BPmCHoAF_ADk@Bm@@k@Bq@@c@@u@@e@Ao@?Q?y@AqAAmBAsBAkAAy@?}@AqAAeB?[CiDAeCAsAAo@AGCkAAUGsAI{AC_@Cu@E}@O{CE_AE_AGgAEc@Ca@C[C[I{@E[Iw@UgBKs@Gk@Ec@Eg@E{@Eq@Cq@CcAGyAG_B?KCi@Ai@Es@Aa@?ACm@A]AWAQC[CKAKCIEM\" }, \"start_location\" : { \"lat\" : 52.9202825, \"lng\" : -1.265676 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"1.9 km\", \"value\" : 1936 }, \"duration\" : { \"text\" : \"3 mins\", \"value\" : 158 }, \"end_location\" : { \"lat\" : 52.9444876, \"lng\" : -1.1908731 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit onto \\u003cb\\u003eDerby Rd/A52\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"wsbbIltlFAAAAAAAAA?AA?ACCCEAECEAGAE?GAG?E?I?G@E?C@E?C@E@C@CNo@BWBS@W?Y?U?YB]CYAW?MEc@AWEe@Iy@Ek@Ec@Gm@Iu@Gm@CSMu@Km@Ic@GWQgAIc@Qs@Qk@Qm@GM_@kA]{@u@qBYu@_@oAWcAQs@_@aB[{Am@wCMk@M{@QiAQuACUUqCEc@Ea@G]CUMm@[oAm@}B}@aD{@_Ds@}Bq@{B[aA_@kAK_@[eAOk@W}@M_@Om@Me@Oe@G_@CWEk@?u@?gB?mA?yA@cA@YB_@H}@@ELkAL_BZ_EDa@A[AKBYB]@_@A[AUGSIW\" }, \"start_location\" : { \"lat\" : 52.93899949999999, \"lng\" : -1.2168652 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"2.3 km\", \"value\" : 2288 }, \"duration\" : { \"text\" : \"4 mins\", \"value\" : 225 }, \"end_location\" : { \"lat\" : 52.9554357, \"lng\" : -1.163082 }, \"html_instructions\" : \"At the roundabout, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit onto \\u003cb\\u003eDerby Rd/A6200\\u003c/b\\u003e\", \"maneuver\" : \"roundabout-left\", \"polyline\" : { \"points\" : \"avcbI|qgFA?AAAAAAAACAAAAAGKEIEKCMAMAO?M@O@M?C@C?C@C?A@C@C?AEcACi@Ea@QkACi@CMKi@EIK]O_@Ug@[g@U]MQKQCEc@m@GIm@y@u@gAW_@S[SYQ]Ui@MW_@gBGi@AIEk@Ce@Eq@E_@C_@EWE]CGGSSi@]{@Uq@Ys@Yu@Ws@GOSi@Mc@Kk@AGIi@I{@Gq@K_AWcAY{@Wu@?AMc@GQEKM[ACKQOQOQ_@e@a@e@]a@QYEIWk@EIYw@e@qAY{@S}@s@qCMO[gAq@mCKa@?[WmAa@uAy@mCUk@g@gAWo@c@}@i@cAWg@w@yAUa@[q@c@cAi@uAI[{@mCq@sCMw@YaBIu@e@cEo@sFg@wDEY\" }, \"start_location\" : { \"lat\" : 52.9444876, \"lng\" : -1.1908731 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"53 m\", \"value\" : 53 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 8 }, \"end_location\" : { \"lat\" : 52.955896, \"lng\" : -1.1630581 }, \"html_instructions\" : \"\\u003cb\\u003eDerby Rd/A6200\\u003c/b\\u003e turns \\u003cb\\u003eleft\\u003c/b\\u003e and becomes \\u003cb\\u003eSt. Helens St\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"ozebIfdbFSKIC_@JMBG?CACA\" }, \"start_location\" : { \"lat\" : 52.9554357, \"lng\" : -1.163082 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"65 m\", \"value\" : 65 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 9 }, \"end_location\" : { \"lat\" : 52.9562079, \"lng\" : -1.1622669 }, \"html_instructions\" : \"\\u003cb\\u003eSt. Helens St\\u003c/b\\u003e turns slightly \\u003cb\\u003eright\\u003c/b\\u003e and becomes \\u003cb\\u003eIlkeston Rd/A609\\u003c/b\\u003e\", \"polyline\" : { \"points\" : \"k}ebIbdbFCGACc@kACGGQCIAGAG?G?I\" }, \"start_location\" : { \"lat\" : 52.955896, \"lng\" : -1.1630581 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"0.4 km\", \"value\" : 396 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 44 }, \"end_location\" : { \"lat\" : 52.9553985, \"lng\" : -1.1566232 }, \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eAlfreton Rd/A610\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eContinue to follow A610\\u003c/div\\u003e\", \"maneuver\" : \"turn-right\", \"polyline\" : { \"points\" : \"i_fbId_bFP]Ng@H_@DSBSB]Ba@A]@cAAiC?qBHsAPkBXkBBWb@sEFe@\" }, \"start_location\" : { \"lat\" : 52.9562079, \"lng\" : -1.1622669 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"62 m\", \"value\" : 62 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 14 }, \"end_location\" : { \"lat\" : 52.95492059999999, \"lng\" : -1.1571125 }, \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eVernon St\\u003c/b\\u003e\", \"maneuver\" : \"turn-right\", \"polyline\" : { \"points\" : \"gzebIz{`F~A`B\" }, \"start_location\" : { \"lat\" : 52.9553985, \"lng\" : -1.1566232 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"90 m\", \"value\" : 90 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 11 }, \"end_location\" : { \"lat\" : 52.9552504, \"lng\" : -1.1583362 }, \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eDerby Rd/A610\\u003c/b\\u003e\", \"maneuver\" : \"turn-right\", \"polyline\" : { \"points\" : \"gwebI|~`Fw@hEIj@\" }, \"start_location\" : { \"lat\" : 52.95492059999999, \"lng\" : -1.1571125 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"46 m\", \"value\" : 46 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 9 }, \"end_location\" : { \"lat\" : 52.95488839999999, \"lng\" : -1.1586711 }, \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eRestricted usage road\\u003c/div\\u003e\", \"maneuver\" : \"turn-left\", \"polyline\" : { \"points\" : \"iyebIrfaFp@j@TT\" }, \"start_location\" : { \"lat\" : 52.9552504, \"lng\" : -1.1583362 }, \"travel_mode\" : \"DRIVING\" }, { \"distance\" : { \"text\" : \"33 m\", \"value\" : 33 }, \"duration\" : { \"text\" : \"1 min\", \"value\" : 11 }, \"end_location\" : { \"lat\" : 52.9547343, \"lng\" : -1.1582607 }, \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eRestricted usage road\\u003c/div\\u003e\", \"maneuver\" : \"turn-left\", \"polyline\" : { \"points\" : \"awebIthaFNq@DSBE@A@C@?\" }, \"start_location\" : { \"lat\" : 52.95488839999999, \"lng\" : -1.1586711 }, \"travel_mode\" : \"DRIVING\" } ], \"via_waypoint\" : [] } ], \"overview_polyline\" : { \"points\" : \"s}gvH`nRqDjBkTcMG@kJuHx@~Vb@bTmSdAeKzHeZdd@oFbKiVeBm\\\\{Dy_@wFuS`AeThUsMpIoR?sf@bb@ai@pVwQrTcPhg@HFMNsW~AgLkWyi@eF}o@bBsm@h]gcApl@aUhIyTqB~CqPyi@i[wb@hCyWzOuc@|DgbB}\\\\q_BqRwv@ga@{_C}JweAtJ{mAkZ{hAck@oViGe\\\\fHsq@z@ct@tWufAll@cNvl@}WdvDvGls@p\\\\feApTheAvG|qAoJfyBwlA~gDobAb{@}`@ru@_S`z@wQjhCa[f{@ub@xc@ab@rUmXvh@qDb|A|PrwC|e@vgDwBd`AmY|yAqr@fxBe^feBkG``AwTlu@iZj]u]tL{w@rCe_Afw@y|@~}@_x@~_@m_@n\\\\gl@zkAkv@jv@m`At]yy@kEcy@nFke@qJkm@on@uh@_]y^ka@q`@eM}i@kh@sh@se@cYeIuc@eJaZ_W__@qVq_@uCcx@r_@eYhMy]aB{a@wNks@o@}_AxPw[tSsXfeA{c@h_AguAfo@es@~Jwc@cDkdBuUirCse@eg@_Vcn@ap@ox@elAw{@{^}ZuLum@oCkVmWoNen@cHwxAmZo}@e[an@{TwNcrAyPw{@goAa\\\\mbAwBsxBePquAuk@hCyqAz`@s}@vmAse@tp@s]fNchAyMyaBaUoo@|EmwCz\\\\sqCxWefAjM}b@x_@sjAflAadAx|Aqn@pmAi{@rU_lAhiAcqBtlAyyAzUsp@jo@gn@jdA}_@zZwl@xPe~@|IysCx|Bsv@rqAg_@xhBmxA|fJckAbuD_fAxqEkmArhD}tAjwC}s@`p@{w@fmAet@boBavAlwC}_AvaBivAvsAueAteAgu@~gAq}C~qG_m@dgA{f@pfBwgAhoEykAdfIcx@heDwkBtoEgi@lo@eb@fKaj@`MybAf`Ae}C|nCyy@|bA_jA~y@i}Ahc@au@hKseCb|@mgBln@i|Alo@_iAdv@gmAbT{x@_Fcp@mEu{@tIiqDcJyg@wHss@qYgy@_j@ob@{Pwg@SegBxw@mlAlAizBbhAcv@xB{o@_Oyl@CouAbn@cyBtwAsdBzuBk~@x|CeeAzeCqb@f\\\\ej@nKqgAdRuaBmIokAir@wqAoG}nByj@ilAhEsjA~b@im@|`@kqAhrAgw@ld@so@v@eqCu~@ab@b@qq@zR_mCbfAke@uEea@c\\\\ap@ed@wh@~AwfAbDyDhGeFmSmMacAoIolCw`B{|A}FcrBaHazAmQwlAaPq}@NwXuYqdAel@cgBtAeWhA}JnAbKt@{@\" }, \"summary\" : \"M25 and M1\", \"warnings\" : [], \"waypoint_order\" : [] } ], \"status\" : \"OK\" }",
					distance, duration);
		}

		@Override
		public String getResponse(URL url) {
			return urlToResponse.get(url);
		}
	}
}
