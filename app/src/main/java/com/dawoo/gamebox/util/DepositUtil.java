package com.dawoo.gamebox.util;

import android.content.Context;

import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.adapter.HomeAdapter.AdapterSet.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rain on 18-3-28.
 */

public class DepositUtil {
    final static String NOTEFILEPATH = "type_note2";
    final static String DEFAULT = "default";
    static Map<String, String[]> notes = new HashMap<>();

    /**
     * 读取assets下的csv文件
     */
    public static LinkedList<String> readAssetsCsv(Context context, String fileName) {
        LinkedList<String> lineList = new LinkedList<>();
        try {
            InputStream is = context.getAssets().open(fileName + ".csv");
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                //   Log.e("lyn_readLine", strLine);
                lineList.add(strLine);
            }
            is.close();
            isr.close();
            br.close();
            return lineList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNoteByCode(String code, boolean isRandom, int type, boolean isfirstView) {
        String note = "";
        if (notes.isEmpty()) {
            List<String> lineList = readAssetsCsv(BoxApplication.getContext(), NOTEFILEPATH);
            for (String item : lineList) {
                if (!item.isEmpty()) {
                    String[] items = item.split(",");
                    if (items.length == 3) {
                        notes.put(items[0], new String[]{items[1], items[2]});
                    }
                }
            }
        }
        String[] getNotes = notes.get(code);
        if (isRandom) {
           // note = "*为了提高对账速度及成功率，当前支付方式已开随机额度，请输入整数存款金额，将随机增加0.01~0.99元";
            note = "•为了提高对账速度及成功率，当前支付方式已开随机额度，请输入整数存款金额，将随机增加0.11~0.99元！";

        } else {
            if ("qq".equals(code)){
                if (type == 1){
                    note = getNotes[0];
                }else {
                    note = "•为了提高对账速度及成功率，当前支付方式已开随机额度，请输入整数存款金额，将随机增加0.11~0.99元！";
                }

            }else {
                note = getNotes[0];
            }


        }

        if (type == 2 || code.equalsIgnoreCase(ConstantValue.BITCOIN)) {
            note += "\n" + getNotes[1];
            note = note.replace("•请先查看入款账号信息或扫描二维码。<br>", "");
            if (code.equals("jd")||code.equals("bd")||code.equals("wechat")||code.equals("qq")||code.equals("alipay")){
                note = note.replace("<br>•为了系统快速完成转账，请输入订单号后5位，以加快系统入款速度。", "");
            }
            if ("easy".equals(code)){
                note = note+"<br>•如有任何疑问，请联系在线客服获取帮助。";
            }
            return note;
        } else {
            if (isfirstView) {
                return note+"<br>•如有任何疑问，请联系在线客服获取帮助。";
            } else {
                note = getNotes[1];
                return note;
            }
        }

    }
}
