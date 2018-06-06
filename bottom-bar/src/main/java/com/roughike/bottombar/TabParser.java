package com.roughike.bottombar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.annotation.XmlRes;
import android.support.v4.content.ContextCompat;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iiro on 21.7.2016.
 */
class TabParser {
    private final Context context;
    private final BottomBarTab.Config defaultTabConfig;
    private final XmlResourceParser parser;

    private ArrayList<BottomBarComponent> tabs;
    private BottomBarTab workingTab;
	private BottomBarButton workingButton;
	private BottomBarEmptyView workingEmpty;

    TabParser(Context context, BottomBarTab.Config defaultTabConfig, @XmlRes int tabsXmlResId) {
        this.context = context;
        this.defaultTabConfig = defaultTabConfig;

        parser = context.getResources().getXml(tabsXmlResId);
        tabs = new ArrayList<>();

        parse();
    }

    private void parse() {
        try {
            parser.next();
            int eventType = parser.getEventType();

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if(eventType == XmlResourceParser.START_TAG) {
					switch (parser.getName()){
						case "tab":
							parseNewTab(parser);
							break;
						case "button":
							parseNewButton(parser);
							break;
						case "empty":
							parseNewEmpty(parser);
							break;
					}
                    parseNewTab(parser);
                } else if(eventType == XmlResourceParser.END_TAG) {
                    if (parser.getName().equals("tab")) {
                        if (workingTab != null) {
                            tabs.add(workingTab);
                            workingTab = null;
                        }
                    }
					if(parser.getName().equals("button")){
						if(workingButton != null){
							tabs.add(workingButton);
							workingButton = null;
						}
					}
					if(parser.getName().equals("empty")){
						if(workingEmpty != null){
							tabs.add(workingEmpty);
							workingEmpty = null;
						}
					}
                }

                eventType = parser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            throw new TabParserException();
        }
    }

	private void parseNewEmpty(XmlResourceParser parser){
		if(workingEmpty == null){
			workingEmpty = new BottomBarEmptyView(context);
		}
		workingEmpty.setIndexInContainer(tabs.size());
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeName(i);

			switch (attrName) {
				case "id":
					workingEmpty.setId(parser.getIdAttributeResourceValue(i));
					break;
			}
		}
	}

	private void parseNewButton(XmlResourceParser parser) {
		if (workingButton == null) {
			workingButton = new BottomBarButton(context);
		}

		workingButton.setIndexInContainer(tabs.size());

		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeName(i);

			switch (attrName) {
				case "id":
					workingButton.setId(parser.getIdAttributeResourceValue(i));
					break;
				case "icon":
					workingButton.setIconResId(parser.getAttributeResourceValue(i, 0));
					break;
				case "title":
					workingButton.setTitle(getTitleValue(i, parser));
					break;
				case "iconColor":
					Integer inActiveColor = getColorValue(i, parser);
					if (inActiveColor != null) {
						workingButton.setIconColor(inActiveColor);
					}
					break;
				case "backgroundColor":
					Integer bg = getColorValue(i, parser);

					if (bg != null) {
						workingButton.setBackgroundColor(bg);
					}
					break;
				case "cornerRadius":
					Integer cornerRadius = parser.getAttributeIntValue(i, 5);

					if (cornerRadius != null) {
						workingButton.setCornerRadius(cornerRadius);
					}
					break;
				case "badgeBackgroundColor":
					Integer badgeBackgroundColor = getColorValue(i, parser);

					if (badgeBackgroundColor != null) {
						workingButton.setBadgeBackgroundColor(badgeBackgroundColor);
					}
					break;
			}
		}
	}


    private void parseNewTab(XmlResourceParser parser) {
        if (workingTab == null) {
            workingTab = tabWithDefaults();
        }

        workingTab.setIndexInContainer(tabs.size());

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attrName = parser.getAttributeName(i);

            switch (attrName) {
                case "id":
                    workingTab.setId(parser.getIdAttributeResourceValue(i));
                    break;
                case "icon":
                    workingTab.setIconResId(parser.getAttributeResourceValue(i, 0));
                    break;
				case "activeIcon":
					workingTab.setActiveIconResId(parser.getAttributeResourceValue(i, 0));
                case "title":
                    workingTab.setTitle(getTitleValue(i, parser));
                    break;
                case "inActiveColor":
                    Integer inActiveColor = getColorValue(i, parser);
                    if (inActiveColor != null) {
                        workingTab.setInActiveColor(inActiveColor);
                    }
                    break;
                case "activeColor":
                    Integer activeColor = getColorValue(i, parser);
                    if (activeColor != null) {
                        workingTab.setActiveColor(activeColor);
                    }
                    break;
                case "barColorWhenSelected":
                    Integer barColorWhenSelected = getColorValue(i, parser);

                    if (barColorWhenSelected != null) {
                        workingTab.setBarColorWhenSelected(barColorWhenSelected);
                    }
                    break;
                case "badgeBackgroundColor":
                    Integer badgeBackgroundColor = getColorValue(i, parser);

                    if (badgeBackgroundColor != null) {
                        workingTab.setBadgeBackgroundColor(badgeBackgroundColor);
                    }
                    break;
            }
        }
    }

    private BottomBarTab tabWithDefaults() {
        BottomBarTab tab = new BottomBarTab(context);
        tab.setConfig(defaultTabConfig);
        return tab;
    }

    private String getTitleValue(int attrIndex, XmlResourceParser parser) {
        int titleResource = parser.getAttributeResourceValue(attrIndex, 0);

        if (titleResource != 0) {
            return context.getString(titleResource);
        }

        return parser.getAttributeValue(attrIndex);
    }

    private Integer getColorValue(int attrIndex, XmlResourceParser parser) {
        int colorResource = parser.getAttributeResourceValue(attrIndex, 0);

        if (colorResource != 0) {
            return ContextCompat.getColor(context, colorResource);
        }

        try {
            return Color.parseColor(parser.getAttributeValue(attrIndex));
        } catch (Exception ignored) {
            return null;
        }
    }

    List<BottomBarComponent> getTabs() {
        return tabs;
    }

    private class TabParserException extends RuntimeException {
    }
}
