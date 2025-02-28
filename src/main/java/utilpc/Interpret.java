package utilpc;

import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.BasisSet;
import common.battle.Treasure;
import common.battle.data.*;
import common.pack.Identifier;
import common.pack.UserProfile;
import common.util.Data;
import common.util.Data.Proc.ProcItem;
import common.util.lang.Formatter;
import common.util.lang.ProcLang;
import common.util.stage.MapColc;
import common.util.stage.MapColc.DefMapColc;
import common.util.unit.Combo;
import common.util.unit.Enemy;
import main.MainBCU;
import page.MainLocale;
import page.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class Interpret extends Data {

	/**
	 * enemy types
	 */
	public static String[] ERARE;

	/**
	 * unit rarities
	 */
	public static String[] RARITY;

	/**
	 * enemy traits
	 */
	public static String[] TRAIT;

	/**
	 * star names
	 */
	public static String[] STAR;

	/**
	 * ability name
	 */
	public static String[] ABIS;

	/**
	 * enemy ability name
	 */
	public static String[] EABI;

	public static String[] SABIS;
	public static String[] TREA;
	public static String[] ATKCONF;
	public static String[] COMF;
	public static String[] COMN;
	public static String[] TCTX;
	public static String[] PCTX;

	/**
	 * treasure orderer
	 */
	public static final int[] TIND = { 0, 1, 18, 19, 20, 21, 22, 23, 2, 3, 4, 5, 24, 25, 26, 27, 28, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 29, 30, 31, 32, 33, 34, 35, 36 };

	/**
	 * treasure grouper
	 */
	public static final int[][] TCOLP = { { 0, 8 }, { 8, 6 }, { 14, 3 }, { 17, 4 }, { 21, 3 }, { 29, 8 } };

	/**
	 * treasure max
	 */
	private static final int[] TMAX = { 30, 30, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 600, 1500, 100,
			100, 100, 30, 30, 30, 30, 30, 10, 300, 300, 600, 600, 600, 30, 0, 0, 0, 0, 0, 0, 0 };

	/**
	 * combo string component
	 */
	private static final String[][] CDP = { { "", "+", "-" }, { "_", "_%", "_f", "Lv._" } };

	/**
	 * combo string formatter
	 */
	private static final int[][] CDC = { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 3 }, { 1, 0 }, { 1, 1 }, { 2, 2 },
			{ 1, 1 }, { 1, 1 }, { 1, 1 }, { 2, 2 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 },
			{ 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } };

	//Filters abilities and procs that are available for enemies. Also gives better organization to the UI
	public static final int[] EABIIND = { ABI_WAVES, ABI_SNIPERI, ABI_TIMEI, ABI_GHOST, ABI_GLASS, ABI_THEMEI };
	public static final int[] EPROCIND = { Data.P_KB, Data.P_STOP, Data.P_SLOW, Data.P_WEAK, Data.P_CRIT, Data.P_WAVE, Data.P_MINIWAVE,
			Data.P_VOLC, Data.P_BARRIER, Data.P_DEMONSHIELD, Data.P_BREAK, Data.P_SHIELDBREAK, Data.P_WARP, Data.P_CURSE, Data.P_SEAL,
			Data.P_SATK, Data.P_POIATK, Data.P_ATKBASE, Data.P_SUMMON, Data.P_MOVEWAVE, Data.P_SNIPER, Data.P_BOSS, Data.P_TIME, Data.P_THEME,
			Data.P_POISON, Data.P_ARMOR, Data.P_SPEED, Data.P_STRONG, Data.P_LETHAL, Data.P_BURROW, Data.P_REVIVE, Data.P_CRITI, Data.P_COUNTER,
			Data.P_IMUATK, Data.P_DMGCUT, Data.P_DMGCAP, Data.P_IMUKB, Data.P_IMUSTOP, Data.P_IMUSLOW, Data.P_IMUWAVE, Data.P_IMUWEAK,
			Data.P_IMUWARP, Data.P_IMUCURSE, Data.P_IMUSEAL, Data.P_IMUMOVING, Data.P_IMUARMOR, Data.P_IMUPOI, Data.P_IMUPOIATK, Data.P_IMUVOLC,
			Data.P_IMUSPEED, Data.P_IMUSUMMON, Data.P_IMUCANNON, Data.P_DEATHSURGE};
	//Filters abilities and procs that are available for units. Also gives better organization to the UI
	public static final int[] UPROCIND = { Data.P_BSTHUNT, Data.P_KB, Data.P_STOP, Data.P_SLOW, Data.P_WEAK, Data.P_BOUNTY, Data.P_CRIT, Data.P_WAVE,
			Data.P_MINIWAVE, Data.P_VOLC, Data.P_BARRIER, Data.P_DEMONSHIELD, Data.P_BREAK, Data.P_SHIELDBREAK, Data.P_WARP, Data.P_CURSE,
			Data.P_SEAL, Data.P_SATK, Data.P_POIATK, Data.P_ATKBASE, Data.P_SUMMON, Data.P_MOVEWAVE, Data.P_SNIPER, Data.P_BOSS, Data.P_TIME,
			Data.P_THEME, Data.P_POISON, Data.P_ARMOR, Data.P_SPEED, Data.P_STRONG, Data.P_LETHAL, Data.P_BURROW, Data.P_REVIVE, Data.P_CRITI,
			Data.P_COUNTER, Data.P_IMUATK, Data.P_DMGCUT, Data.P_DMGCAP, Data.P_IMUKB, Data.P_IMUSTOP, Data.P_IMUSLOW, Data.P_IMUWAVE,
			Data.P_IMUWEAK, Data.P_IMUWARP, Data.P_IMUCURSE, Data.P_IMUSEAL, Data.P_IMUMOVING, Data.P_IMUARMOR, Data.P_IMUPOI, Data.P_IMUPOIATK,
			Data.P_IMUVOLC, Data.P_IMUSPEED, Data.P_IMUSUMMON, Data.P_DEATHSURGE};

	private static final DecimalFormat df;

	static {
		redefine();

		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		df = (DecimalFormat) nf;
	}

	public static void loadCannonMax() {
		for (int i = 1; i <= Treasure.curveData.size(); i++)
			TMAX[29 + i] = Treasure.curveData.get(i).max;
	}

	public static boolean allRangeSame(MaskEntity me) {
		if (me instanceof CustomEntity) {
			List<Integer> near = new ArrayList<>();
			List<Integer> far = new ArrayList<>();

			for (AtkDataModel atk : ((CustomEntity) me).atks) {
				near.add(atk.getShortPoint());
				far.add(atk.getLongPoint());
			}

			if (near.isEmpty()) {
				return true;
			}

			for (int n : near) {
				if (n != near.get(0)) {
					return false;
				}
			}

			for (int f : far) {
				if (f != far.get(0)) {
					return false;
				}
			}
		} else {
			for (int i = 1; i < me.getAtkCount(); i++) {
				if (me.getAtkModel(i).getShortPoint() != me.getAtkModel(0).getShortPoint() || me.getAtkModel(i).getLongPoint() != me.getAtkModel(0).getLongPoint())
					return false;
			}
		}

		return true;
	}

	public static String comboInfo(Combo c, BasisSet b) {
		return combo(c.type, CommonStatic.getBCAssets().values[c.type][c.lv], b);
	}

	public static class ProcDisplay {
		private String text;
		private ImageIcon icon = null;
		public ProcDisplay(String desc, BufferedImage img) {
			text = desc;
			if (img != null)
				icon = new ImageIcon(img);
		}
		@Override
		public String toString() { return text; }
		public ImageIcon getIcon() { return icon; }
	}

	public static List<ProcDisplay> getAbi(MaskEntity me) {
		int tb = me.touchBase();
		final MaskAtk ma;

		if (me.getAtkCount() == 1) {
			ma = me.getAtkModel(0);
		} else {
			ma = me.getRepAtk();
		}

		int lds;
		int ldr;

		if (allRangeSame(me)) {
			lds = me.getAtkModel(0).getShortPoint();
			ldr = me.getAtkModel(0).getLongPoint() - me.getAtkModel(0).getShortPoint();
		} else {
			lds = ma.getShortPoint();
			ldr = ma.getLongPoint() - ma.getShortPoint();
		}


		List<ProcDisplay> l = new ArrayList<>();
		if (!allRangeSame(me)) {
			LinkedHashMap<String, List<Integer>> LDInts = new LinkedHashMap<>();
			MaskAtk[] atks = me.getAtks();
			List<BufferedImage> ics = new ArrayList<>();

			for (int i = 0; i < atks.length ; i++ ) {
				int rs = atks[i].getShortPoint();
				int rl = atks[i].getLongPoint();
				if (rs == 0 && rl == 0)
					continue;
				String LDData = Page.get(MainLocale.UTIL, "ld0") + ": " + tb + ", " + Page.get(MainLocale.UTIL, "ld1")
						+ ": " + rs + "~" + rl + ", " + Page.get(MainLocale.UTIL, "ld2") + ": " + (rl - rs);
				if (LDInts.containsKey(LDData)) {
					List<Integer> li = LDInts.get(LDData);
					li.add(i + 1);
				} else {
					List<Integer> li = new ArrayList<>();
					li.add(i + 1);

					LDInts.put(LDData, li);
				}
				if (atks[i].isOmni())
					ics.add(UtilPC.getIcon(2, ATK_OMNI));
				else
					ics.add(UtilPC.getIcon(2, ATK_LD));
			}

			int i = 0;
			for (String key : LDInts.keySet()) {
				List<Integer> inds = LDInts.get(key);
				if (inds == null) {
					l.add(new ProcDisplay(key, ics.get(i++)));
				} else {
					if (inds.size() == me.getAtkCount()) {
						l.add(new ProcDisplay(key, ics.get(i++)));
					} else {
						l.add(new ProcDisplay(key + " " + getAtkNumbers(inds), ics.get(i++)));
					}
				}
			}
		} else if (lds != 0 || ldr != 0) {
			int p0 = Math.min(lds, lds + ldr);
			int p1 = Math.max(lds, lds + ldr);
			int r = Math.abs(ldr);
			BufferedImage bi;
			if (me.isOmni()) {
				bi = UtilPC.getIcon(2, ATK_OMNI);
			} else {
				bi = UtilPC.getIcon(2, ATK_LD);
			}
			l.add(new ProcDisplay(Page.get(MainLocale.UTIL, "ld0") + ": " + tb + ", " + Page.get(MainLocale.UTIL, "ld1") + ": " + p0 + "~" + p1 + ", "
					+ Page.get(MainLocale.UTIL, "ld2") + ": " + r, bi));
		}
		AtkDataModel rev = me.getRevenge();
		for (int z = 0; z < 6; z++) {
			if (rev != null) {
				int revs = rev.getShortPoint();
				int revl = rev.getLongPoint();
				if (revs != 0 || revl != 0) {
					BufferedImage bi;
					if (rev.isOmni())
						bi = (UtilPC.getIcon(2, ATK_OMNI));
					else
						bi = (UtilPC.getIcon(2, ATK_LD));
					l.add(new ProcDisplay(Page.get(MainLocale.UTIL, "ld1") + ": " + revs + "~" + revl +
							", " + Page.get(MainLocale.UTIL, "ld2") + ": " + (revl - revs) +
							" [" + Page.get(MainLocale.UTIL, "aa" + (z + 6)) + "]", bi));
				}
			}
			switch (z) {
				case 0:
					rev = me.getResurrection();
					break;
				case 1:
					rev = me.getCounter();
					break;
				case 2:
					rev = me.getGouge();
					break;
				case 3:
					rev = me.getResurface();
					break;
				case 4:
					rev = me.getRevive();
					break;
				default:
					rev = null;
			}
		}
		String imu = Page.get(MainLocale.UTIL, "imu");
		for (int i = 0; i < ABIS.length; i++)
			if (((me.getAbi() >> i) & 1) > 0)
				if (ABIS[i].startsWith("IMU"))
					l.add(new ProcDisplay(imu + ABIS[i].substring(3), UtilPC.getIcon(0, i)));
				else {
					l.add(new ProcDisplay(ABIS[i], UtilPC.getIcon(0, i)));
				}
		return l;
	}

	public static String[] getComboFilter(int n) {
		int[] res = CommonStatic.getBCAssets().filter[n];
		String[] strs = new String[res.length];
		for (int i = 0; i < res.length; i++)
			strs[i] = COMN[res[i]];
		return strs;
	}

	public static int getComp(int ind, Treasure t) {
		int ans = -2;
		for (int i = 0; i < TCOLP[ind][1]; i++) {
			int temp = getValue(TIND[i + TCOLP[ind][0]], t);
			if (ans == -2)
				ans = temp;
			else if (ans != temp)
				return -1;
		}
		return ans;
	}

	public static List<ProcDisplay> getProc(MaskEntity du, boolean isEnemy, double[] magnification) {
		Formatter.Context ctx = new Formatter.Context(isEnemy, MainBCU.seconds, magnification);
		final boolean common;

		if(du instanceof CustomEntity) {
			common = ((CustomEntity) du).common;
		} else {
			common = true;
		}

		ArrayList<ProcDisplay> l = new ArrayList<>();
		List<Boolean> share = new ArrayList<>();

		if(common) {
			MaskAtk ma = du.getRepAtk();

			for(int i = 0; i < Data.PROC_TOT; i++) {
				ProcItem item = ma.getProc().getArr(i);

				if(!item.exists())
					continue;
				if (du instanceof DefaultData) {
					boolean p = false;
					if (ma.getProc().sharable(i))
						p = true;
					else
						for (int pr : BCShareable)
							if (pr == i) {
								p = true;
								break;
							}
					share.add(p);
				}
				String format = ProcLang.get().get(i).format;
				String formatted = Formatter.format(format, item, ctx);

				l.add(new ProcDisplay(formatted, UtilPC.getIcon(1, i)));
			}
		} else {
			LinkedHashMap<String, List<Integer>> atkMap = new LinkedHashMap<>();
			List<BufferedImage> procIcons = new ArrayList<>();

			MaskAtk ma = du.getRepAtk();

			for (int i = 0; i < Data.PROC_TOT; i++) {
				ProcItem item = ma.getProc().getArr(i);

				if (!item.exists() || !ma.getProc().sharable(i))
					continue;

				String format = ProcLang.get().get(i).format;
				String formatted = Formatter.format(format, item, ctx);
				l.add(new ProcDisplay(formatted, UtilPC.getIcon(1, i)));
			}

			for (int i = 0; i < du.getAtkCount(); i++) {
				ma = du.getAtkModel(i);

				for (int j = 0; j < Data.PROC_TOT; j++) {
					ProcItem item = ma.getProc().getArr(j);

					if (!item.exists() || ma.getProc().sharable(j))
						continue;

					String format = ProcLang.get().get(j).format;
					String formatted = Formatter.format(format, item, ctx);

					if (atkMap.containsKey(formatted)) {
						List<Integer> inds = atkMap.get(formatted);

						inds.add(i + 1);
					} else {
						List<Integer> inds = new ArrayList<>();

						inds.add(i + 1);

						atkMap.put(formatted, inds);
						procIcons.add(UtilPC.getIcon(1, j));
					}
				}
			}

			int i = 0;
			for (String key : atkMap.keySet()) {
				List<Integer> inds = atkMap.get(key);

				if (inds == null) {
					l.add(new ProcDisplay(key, procIcons.get(i++)));
				} else {
					if (inds.size() == du.getAtkCount()) {
						l.add(new ProcDisplay(key, procIcons.get(i++)));
					} else {
						l.add(new ProcDisplay(key + " " + getAtkNumbers(inds), procIcons.get(i++)));
					}
				}
			}
		}

		if (du instanceof DefaultData && !((DefaultData)du).isCommon()) {
			int[][] atkData = du.rawAtkData();
			List<Integer> atks = new ArrayList<>();
			for (int i = 0; i < atkData.length; i++)
				if (atkData[i][2] == 1)
					atks.add(i + 1);
			for (int i = 0; i < l.size(); i++)
				if (!share.get(i))
					l.get(i).text = l.get(i).text + " " + getAtkNumbers(atks);
		}

		MaskAtk rev = du.getRevenge();
		for (int i = 0; i < 6; i++) {
			if (rev != null) {
				for(int j = 0; j < Data.PROC_TOT; j++) {
					ProcItem item = rev.getProc().getArr(j);

					if(!item.exists() || rev.getProc().sharable(j))
						continue;

					String format = ProcLang.get().get(j).format;
					String formatted = Formatter.format(format, item, ctx);
					l.add(new ProcDisplay(formatted + " [" + Page.get(MainLocale.UTIL, "aa" + (6 + i)) + "]", UtilPC.getIcon(1, j)));
				}
			}
			switch (i) {
				case 0:
					rev = du.getResurrection();
					break;
				case 1:
					rev = du.getCounter();
					break;
				case 2:
					rev = du.getGouge();
					break;
				case 3:
					rev = du.getResurface();
					break;
				case 4:
					rev = du.getRevive();
					break;
				default:
					rev = null;
			}
		}

		return l;
	}

	public static String getTrait(String[] cTraits, int star) {
		StringBuilder ans = new StringBuilder();
		for (String cTrait : cTraits) ans.append(cTrait).append(", ");
		if (star > 0)
			ans.append(STAR[star]);

		String res = ans.toString();

		if(res.endsWith(", ")) {
			res = res.substring(0, res.length() - 2);
		}

		return res;
	}

	public static int getValue(int ind, Treasure t) {
		if (ind == 0)
			return t.tech[LV_RES];
		else if (ind == 1)
			return t.tech[LV_ACC];
		else if (ind == 2)
			return t.trea[T_ATK];
		else if (ind == 3)
			return t.trea[T_DEF];
		else if (ind == 4)
			return t.trea[T_RES];
		else if (ind == 5)
			return t.trea[T_ACC];
		else if (ind == 6)
			return t.fruit[T_RED];
		else if (ind == 7)
			return t.fruit[T_FLOAT];
		else if (ind == 8)
			return t.fruit[T_BLACK];
		else if (ind == 9)
			return t.fruit[T_ANGEL];
		else if (ind == 10)
			return t.fruit[T_METAL];
		else if (ind == 11)
			return t.fruit[T_ZOMBIE];
		else if (ind == 12)
			return t.fruit[T_ALIEN];
		else if (ind == 13)
			return t.alien;
		else if (ind == 14)
			return t.star;
		else if (ind == 15)
			return t.gods[0];
		else if (ind == 16)
			return t.gods[1];
		else if (ind == 17)
			return t.gods[2];
		else if (ind == 18)
			return t.tech[LV_BASE];
		else if (ind == 19)
			return t.tech[LV_WORK];
		else if (ind == 20)
			return t.tech[LV_WALT];
		else if (ind == 21)
			return t.tech[LV_RECH];
		else if (ind == 22)
			return t.tech[LV_CATK];
		else if (ind == 23)
			return t.tech[LV_CRG];
		else if (ind == 24)
			return t.trea[T_WORK];
		else if (ind == 25)
			return t.trea[T_WALT];
		else if (ind == 26)
			return t.trea[T_RECH];
		else if (ind == 27)
			return t.trea[T_CATK];
		else if (ind == 28)
			return t.trea[T_BASE];
		else if (ind == 29)
			return t.bslv[BASE_H];
		else if (ind == 30)
			return t.bslv[BASE_SLOW];
		else if (ind == 31)
			return t.bslv[BASE_WALL];
		else if (ind == 32)
			return t.bslv[BASE_STOP];
		else if (ind == 33)
			return t.bslv[BASE_WATER];
		else if (ind == 34)
			return t.bslv[BASE_GROUND];
		else if (ind == 35)
			return t.bslv[BASE_BARRIER];
		else if (ind == 36)
			return t.bslv[BASE_CURSE];
		return -1;
	}

	public static boolean isER(Enemy e, int t) {
		if (t == 0)
			return e.inDic;
		else if (t == 1)
			return e.de.getStar() == 1;
		else if (t == 5)
			return !e.id.pack.equals(Identifier.DEF);

		List<MapColc> lis = e.findMap();
		boolean colab = false;
		final int recurring;
		if (e.de instanceof DataEnemy)
			recurring = e.findApp(DefMapColc.getMap("N")).size() + e.findApp(DefMapColc.getMap("A")).size() + e.findApp(DefMapColc.getMap("CH")).size();
		else
			recurring = e.findApp(UserProfile.getUserPack(e.id.pack).mc).size();
		if (lis.contains(DefMapColc.getMap("C")))
			if (lis.size() == 1)
				colab = true;
			else {
				colab = lis.contains(DefMapColc.getMap("R")) || lis.contains(DefMapColc.getMap("CH")) || lis.contains(DefMapColc.getMap("CA"));
				if (lis.size() > 2)
					colab &= recurring == 0;
			}
		if (t == 2)
			return !colab;
		else if (t == 3)
			return e.id.pack.equals(Identifier.DEF) && !e.inDic;
		else if (t == 4)
			return colab;
		else if (t == 6)
			return recurring > 1;
		return false;
	}

	public static boolean isType(MaskEntity de, int type) {
		int[][] raw = de.rawAtkData();
		if (type == 0)
			return !de.isRange();
		else if (type == 1)
			return de.isRange();
		else if (type == 2)
			return de.isLD();
		else if (type == 3)
			return raw.length > 1;
		else if (type == 4)
			return de.isOmni();
		else if (type == 5)
			return de.getTBA() + raw[0][1] < de.getItv() / 2;
		else if (type == 6)
			return de.getRevenge() != null;
		else if (type == 7)
			return de.getResurrection() != null;
		return false;
	}

	public static void redefine() {
		ERARE = Page.get(MainLocale.UTIL, "er", 7);
		RARITY = Page.get(MainLocale.UTIL, "r", 6);
		TRAIT = Page.get(MainLocale.UTIL, "c", TRAIT_TOT);
		STAR = Page.get(MainLocale.UTIL, "s", 5);
		ABIS = Page.get(MainLocale.UTIL, "a", ABI_TOT);
		SABIS = Page.get(MainLocale.UTIL, "sa", ABI_TOT);
		ATKCONF = Page.get(MainLocale.UTIL, "aa", 8);
		TREA = Page.get(MainLocale.UTIL, "t", 37);
		COMF = Page.get(MainLocale.UTIL, "na", 6);
		COMN = Page.get(MainLocale.UTIL, "nb", 25);
		TCTX = Page.get(MainLocale.UTIL, "tc", 6);
		PCTX = Page.get(MainLocale.UTIL, "aq", 63);
		EABI = new String[EABIIND.length];
		for (int i = 0; i < EABI.length; i++)
			EABI[i] = SABIS[EABIIND[i]];
	}

	public static void setComp(int ind, int v, BasisSet b) {
		for (int i = 0; i < TCOLP[ind][1]; i++)
			setValue(TIND[i + TCOLP[ind][0]], v, b);
	}

	public static void setValue(int ind, int v, BasisSet b) {
		setVal(ind, v, b.t());
		for (BasisLU bl : b.lb)
			setVal(ind, v, bl.t());
	}

	private static String combo(int t, int val, BasisSet b) {
		int[] con = CDC[t];
		if (t == C_RESP) {
			double research = (b.t().tech[LV_RES] - 1) * 6 + b.t().trea[T_RES] * 0.3;
			return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replaceAll("_", "" + research * val / 100);
		} else {
			return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replaceAll("_", "" + val);
		}
	}

	private static void setVal(int ind, int v, Treasure t) {

		if (v < 0)
			v = 0;
		v = Math.min(v, TMAX[ind]);

		if (ind == 0)
			t.tech[LV_RES] = Math.max(v, 1);
		else if (ind == 1)
			t.tech[LV_ACC] = Math.max(v, 1);
		else if (ind == 2)
			t.trea[T_ATK] = v;
		else if (ind == 3)
			t.trea[T_DEF] = v;
		else if (ind == 4)
			t.trea[T_RES] = v;
		else if (ind == 5)
			t.trea[T_ACC] = v;
		else if (ind == 6)
			t.fruit[T_RED] = v;
		else if (ind == 7)
			t.fruit[T_FLOAT] = v;
		else if (ind == 8)
			t.fruit[T_BLACK] = v;
		else if (ind == 9)
			t.fruit[T_ANGEL] = v;
		else if (ind == 10)
			t.fruit[T_METAL] = v;
		else if (ind == 11)
			t.fruit[T_ZOMBIE] = v;
		else if (ind == 12)
			t.fruit[T_ALIEN] = v;
		else if (ind == 13)
			t.alien = v;
		else if (ind == 14)
			t.star = v;
		else if (ind == 15)
			t.gods[0] = v;
		else if (ind == 16)
			t.gods[1] = v;
		else if (ind == 17)
			t.gods[2] = v;
		else if (ind == 18)
			t.tech[LV_BASE] = Math.max(v, 1);
		else if (ind == 19)
			t.tech[LV_WORK] = Math.max(v, 1);
		else if (ind == 20)
			t.tech[LV_WALT] = Math.max(v, 1);
		else if (ind == 21)
			t.tech[LV_RECH] = Math.max(v, 1);
		else if (ind == 22)
			t.tech[LV_CATK] = Math.max(v, 1);
		else if (ind == 23)
			t.tech[LV_CRG] = Math.max(v, 1);
		else if (ind == 24)
			t.trea[T_WORK] = v;
		else if (ind == 25)
			t.trea[T_WALT] = v;
		else if (ind == 26)
			t.trea[T_RECH] = v;
		else if (ind == 27)
			t.trea[T_CATK] = v;
		else if (ind == 28)
			t.trea[T_BASE] = v;
		else if (ind == 29)
			t.bslv[BASE_H] = v;
		else if (ind == 30)
			t.bslv[BASE_SLOW] = v;
		else if (ind == 31)
			t.bslv[BASE_WALL] = v;
		else if (ind == 32)
			t.bslv[BASE_STOP] = v;
		else if (ind == 33)
			t.bslv[BASE_WATER] = v;
		else if (ind == 34)
			t.bslv[BASE_GROUND] = v;
		else if (ind == 35)
			t.bslv[BASE_BARRIER] = v;
		else if (ind == 36)
			t.bslv[BASE_CURSE] = v;
	}

	private static String getAtkNumbers(List<Integer> inds) {
		StringBuilder builder = new StringBuilder("[");

		switch (CommonStatic.getConfig().lang) {
			case 1:
				builder.append("第 ");

				for(int i = 0; i < inds.size(); i++) {
					builder.append(inds.get(i));

					if(i < inds.size() -1) {
						builder.append(", ");
					}
				}

				return builder.append(" 次攻擊]").toString();
			case 2:
				for(int i = 0; i < inds.size(); i++) {
					builder.append(inds.get(i));

					if(i < inds.size() - 1) {
						builder.append(", ");
					}
				}

				return builder.append(" 번째 공격]").toString();
			case 3:
				for(int i = 0; i < inds.size(); i++) {
					builder.append(inds.get(i));

					if(i < inds.size() - 1) {
						builder.append(", ");
					}
				}

				return builder.append(" 回目の攻撃]").toString();
			default:
				for (int i = 0; i < inds.size(); i++) {
					builder.append(getNumberExtension(inds.get(i)));

					if (i < inds.size() - 1) {
						builder.append(", ");
					}
				}

				return builder.append(" Attack]").toString();
		}
	}

	private static String getNumberExtension(int i) {
		if(i != 11 && i % 10 == 1) {
			return i + "st";
		} else if(i != 12 && i % 10 == 2) {
			return i + "nd";
		} else if(i != 13 && i % 10 == 3) {
			return i + "rd";
		} else {
			return i + "th";
		}
	}

	public static double formatDouble(double number, int decimalPlaces) {
		String format = "#." + new String(new char[decimalPlaces]).replace("\0", "#");
		df.applyPattern(format);
		return Double.parseDouble(df.format(number));
	}

	public static void setUnderline(JLabel label) {
		label.addMouseListener(new MouseAdapter() {
			String text;

			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel j = (JLabel) e.getComponent();

				if (text == null || !j.getText().equals("<html><u>" + text + "</u></html>")) {
					text = j.getText();
					j.setText("<html><u>" + j.getText() + "</u></html>");
				} else {
					j.setText(text);
				}
			}
		});

		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}