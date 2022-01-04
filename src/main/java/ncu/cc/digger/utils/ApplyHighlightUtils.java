package ncu.cc.digger.utils;

import ncu.cc.digger.models.AutocompleteModel;

import java.util.List;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ApplyHighlightUtils {
    public static void apply(List<AutocompleteModel> list, String needle) {
        for (AutocompleteModel model: list) {
            // to highlight label in which contain needle
            String label = model.getLabel();

            int index = label.toLowerCase().indexOf(needle);

            if (index >= 0) {
                model.setLabel(String.format("%s<span class='hl_results'>%s</span>%s",
                        label.substring(0, index),
                        label.substring(index, index + needle.length()),
                        label.substring(index + needle.length())));
            }
        }
        // '<span class="hl_results">' . mb_substr($label, $pos, $len) . '</span>';
    }
}
