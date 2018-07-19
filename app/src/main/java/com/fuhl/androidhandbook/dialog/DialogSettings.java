package com.fuhl.androidhandbook.dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tony  自动生成帅哥一枚，谁用谁知道
 * @date 2018/7/19
 */
public class DialogSettings {
    /**
     * 对话框队列，当一个对话框处于启动状态时，
     * 它是阻塞的，除非关掉它，下一个对话框才会显示
     */
    public static List<BaseDialog> dialogList = new ArrayList<>();

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;

    public static final int TYPE_MATERIAL = 0;
    public static final int TYPE_KONGZUE = 1;
    public static final int TYPE_IOS = 2;

    //是否打印日志
    public static boolean DEBUGMODE = true;

    /*
     *  决定等待框、提示框以及iOS风格的对话框是否启用模糊背景
     */
    public static boolean use_blur = true;

    /*
     *  决定对话框的样式
     *  请使用 TYPE_MATERIAL、TYPE_KONGZUE、TYPE_IOS 赋值
     */
    public static int currenType = 0;

    /*
     *  决定对话框的模式（亮色和暗色两种）
     *  请使用 THEME_LIGHT、THEME_DARK 赋值
     */
    public static int dialog_theme = 0;

    /*
     *  决定对话框的默认背景色
     */
    public static int dialog_background_color = -1;

    /*
     *  决定提示框的模式（亮色和暗色两种）
     *  请使用 THEME_LIGHT、THEME_DARK 赋值
     */
    public static int tip_theme = 1;


    //文字大小设定
    //注意，此值必须大于0才生效，否则使用默认值。另外，我们使用的是dp单位，非sp单位，若有特殊需要请自行转换
    //另外，暂时不支持Material风格对话框设定字体大小
    /*
     *  决定对话框标题字样大小
     *  当值<=0时使用默认大小
     */
    public static int dialog_title_text_size = 0;
    /*
     *  决定对话框内容文字字样大小
     *  当值<=0时使用默认大小
     */
    public static int dialog_message_text_size = 0;
    /*
     *  决定输入框输入文本字样大小
     *  当值<=0时使用默认大小
     */
    public static int dialog_input_text_size = 0;
    /*
     *  决定输入框按钮字样大小
     *  当值<=0时使用默认大小
     */
    public static int dialog_button_text_size = 0;
    /*
     *  决定提示框字样大小
     *  当值<=0时使用默认大小
     */
    public static int tip_text_size = 0;
    /*
     *  决定菜单文字字样大小
     *  当值<=0时使用默认大小
     */
    public static int dialog_menu_text_size = 0;

    /*
     *  决定iOS风格时，默认按钮文字颜色(Color)
     *  当值=-1时使用默认蓝色
     */
    public static int ios_normal_button_color = -1;

    public static void showNextDialog() {
        if (!dialogList.isEmpty()) {
            if (!dialogList.get(0).isDialogShown) {
                dialogList.get(0).showDialog();
            }
        }
    }

    public static void unloadAllDialog(){
        try{
            for (BaseDialog baseDialog:dialogList){
                baseDialog.doDismiss();
            }
            dialogList = new ArrayList<>();
            LoadingDialog.dismiss();
        }catch (Exception e){
            if (DEBUGMODE) {
                e.printStackTrace();
            }
        }
    }
}
