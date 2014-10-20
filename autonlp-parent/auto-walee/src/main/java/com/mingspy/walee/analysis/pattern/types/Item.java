package com.mingspy.walee.analysis.pattern.types;

import com.mingspy.walee.core.Slot;

/**
 * Pattern中的每个子元素。
 * @see Pattern
 * @author xiuleili
 *
 */
public class Item
{
    public static final String STAR = "*"; // 通配符
    public static final String STARTWITH = "^"; // 开始
    public static final String ENDWITH = "$"; // 结束
    public static final int SLOT_SCORE = 80;
    public static final int KEY_SCORE = 60;
    public static final int WORD_SCORE = 100;


    protected ItemType type;
    protected String key;
    protected int score;

    public Item(String w)
    {
        key = w;
        if (w.equals(STAR) || w.equals(STARTWITH) || w.equals(ENDWITH)) {
            type = ItemType.KEY;
            score = KEY_SCORE;
        } else if (w.startsWith(Slot.SLOT_START) && w.endsWith(Slot.SLOT_END)) {
            type = ItemType.SLOT;
            score = SLOT_SCORE;
        } else {
            type = ItemType.WORD;
            score = WORD_SCORE;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("[");
        sb.append(type);
        sb.append("]");
        return sb.toString();
    }

    public Item(Item it)
    {
        key = it.key;
        score = it.score;
        type = it.type;
    }

    public ItemType getType()
    {
        return type;
    }

    public void setType(ItemType type)
    {
        this.type = type;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public int getScore()
    {
        if (score <= 0) {
            if (type == ItemType.SLOT) {
                score = SLOT_SCORE;
            } else if (type == ItemType.KEY) {
                score = KEY_SCORE;
            } else if (type == ItemType.WORD) {
                score = WORD_SCORE;
            }
        }
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Item) {
            if (((Item) obj).key.equalsIgnoreCase(this.key)) {
                return true;
            }
        } else if (obj instanceof String) {
            if (((String) obj).equalsIgnoreCase(this.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        if (key == null)
            return 0;
        return key.hashCode();
    }

    public boolean isstar()
    {
        return STAR.equals(key);
    }
}