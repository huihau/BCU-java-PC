package util.pack;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import util.anim.AnimD;
import util.anim.ImgCut;
import util.anim.MaAnim;
import util.anim.MaModel;
import util.system.VImg;

public class EffAnim extends AnimD {

	public static EffAnim[] effas = new EffAnim[A_TOT];

	public static void read() {
		String stre = "./org/battle/e1/set_enemy001_zombie";
		VImg ve = new VImg(stre + ".png");
		ImgCut ice = ImgCut.newIns(stre + ".imgcut");
		String stra = "./org/battle/a/";
		VImg va = new VImg(stra + "000_a.png");
		ImgCut ica = ImgCut.newIns(stra + "000_a.imgcut");
		String ski = "skill00";
		String[] stfs = new String[4];
		VImg[] vfs = new VImg[4];
		ImgCut[] icfs = new ImgCut[4];
		for (int i = 0; i < 4; i++) {
			stfs[i] = "./org/battle/s" + i + "/";
			vfs[i] = new VImg(stfs[i] + ski + i + ".png");
			icfs[i] = ImgCut.newIns(stfs[i] + ski + i + ".imgcut");
		}
		String[] temp = new String[] { "" };
		effas[A_SHOCKWAVE] = new EffAnim(stra + "boss_welcome", va, ica, temp);
		effas[A_CRIT] = new EffAnim(stra + "critical", va, ica, temp);
		temp = new String[] { "_hb", "_sw", "_ass" };
		effas[A_KB] = new EffAnim(stra + "kb", va, ica, temp);

		temp = new String[] { "_revive", "_down" };
		effas[A_ZOMBIE] = new EffAnim(stre, ve, ice, temp);
		effas[A_U_ZOMBIE] = new EffAnim(stre, ve, ice, temp);
		effas[A_U_ZOMBIE].rev = true;
		temp = new String[] { "" };
		ski = "skill_";
		for (int i = 0; i < A_PATH.length; i++) {
			String path = stfs[0] + A_PATH[i] + "/" + ski + A_PATH[i];
			effas[i * 2] = new EffAnim(path, vfs[0], icfs[0], temp);
			effas[i * 2 + 1] = new EffAnim(path + "_e", vfs[0], icfs[0], temp);
		}
		effas[A_EFF_INV] = new EffAnim(stfs[0] + ski + "effect_invalid", vfs[0], icfs[0], temp);
		effas[A_EFF_DEF] = new EffAnim(stfs[0] + ski + "effectdef", vfs[0], icfs[0], temp);
		effas[A_Z_STRONG] = new EffAnim(stfs[1] + ski + "zombie_strong", vfs[1], icfs[1], temp);
		temp = new String[] { "_breaker", "_destruction", "_during", "_start", "_end" };
		effas[A_B] = new EffAnim(stfs[2] + ski + "barrier", vfs[2], icfs[2], temp);
		effas[A_U_B] = new EffAnim(stfs[2] + ski + "barrier", vfs[2], icfs[2], temp);
		effas[A_U_B].rev = true;
		temp = new String[] { "_breaker", "_destruction" };
		effas[A_E_B] = new EffAnim(stfs[2] + ski + "barrier_e", vfs[2], icfs[2], temp);
		effas[A_U_E_B] = new EffAnim(stfs[2] + ski + "barrier_e", vfs[2], icfs[2], temp);
		effas[A_U_E_B].rev = true;
		temp = new String[] { "_entrance", "_exit" };
		effas[A_W] = new EffAnim(stfs[2] + ski + "warp", vfs[2], icfs[2], temp);
		effas[A_W_C] = new EffAnim(stfs[2] + ski + "warp_chara", vfs[2], icfs[2], temp);
		temp = new String[] { "" };
		effas[A_CURSE] = new EffAnim(stfs[3] + ski + "curse", vfs[3], icfs[3], temp);
		VImg vseal = new VImg("./org/battle/s3/skill003.png");
		excColor(vseal.getImg(), (is) -> (is[0] << 24 | is[1] << 16 | is[3] << 8 | is[2]));
		effas[A_SEAL] = new EffAnim(stfs[3] + ski + "curse", vseal, icfs[3], temp);
		VImg vpois = new VImg("./org/battle/s3/poison.png");
		effas[A_POISON] = new EffAnim(stfs[3] + ski + "curse", vpois, icfs[3], temp);
		String strs = "./org/battle/sniper/";
		String strm = "img043";
		VImg vis = new VImg(strs + strm + ".png");
		ImgCut ics = ImgCut.newIns(strs + strm + ".imgcut");
		temp = new String[] { "00", "01" };
		effas[A_SNIPER] = new EffAnim(strs + "000_snyaipa", vis, ics, temp);
	}

	private static void excColor(BufferedImage b0, Function<int[], Integer> f) {
		int w = b0.getWidth();
		int h = b0.getHeight();
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int p = b0.getRGB(i, j);
				int b = p & 255;
				int g = p >> 8 & 255;
				int r = p >> 16 & 255;
				int a = p >> 24;
				p = f.apply(new int[] { a, r, g, b });
				b0.setRGB(i, j, p);
			}
	}

	private VImg vimg;
	private String[] strs;
	private boolean rev;

	public EffAnim(String st, VImg vi, ImgCut ic, String[] anims) {
		super(st);
		vimg = vi;
		imgcut = ic;
		strs = anims;

	}

	@Override
	public void load() {
		loaded = true;
		parts = imgcut.cut(vimg.getImg());
		mamodel = MaModel.newIns(str + ".mamodel");
		anims = new MaAnim[strs.length];
		for (int i = 0; i < strs.length; i++)
			anims[i] = MaAnim.newIns(str + strs[i] + ".maanim");
		if (rev)
			revert();
	}

	@Override
	public String[] names() {
		String[] names = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			names[i] = strs[i];
			if (names[i].length() == 0)
				names[i] = "null";
		}
		return names;
	}

	@Override
	public String toString() {
		String[] ss = str.split("/");
		return ss[ss.length - 1];
	}

}
