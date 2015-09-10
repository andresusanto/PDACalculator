/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalkulator;

import java.util.*;

public class PDA {
    private Stack stack;
    private int state;
    private boolean minusstate;
    
    public float Count(String in)
    {
        // kamus
        
        StringBuilder tmps = new StringBuilder();
        char tmpc, opr;
        float tmp1, tmp2, res;
       
        stack = new Stack();
        state = 0;
        res = 0;
        minusstate = false;
        
        // baca pita karakter 1 per 1
        for (char c : in.toCharArray())
        {
            // menangani operator +
            if (c == '+' || c == '-' || c == '*' || c == '/' || c =='(' || c ==')')
            {
                if (tmps.length() > 0) 
                {
                    stack.push(tmps.toString()); // dipush ke stack
                    tmps = new StringBuilder();  // kalo udah hapus string buildernya
                }
                if (c == '+' || c == '-' || c == '*' || c == '/' || c ==')')
                {
                    if (state == 1 || state == 3)
                    {
                        tmp1 = Float.parseFloat(stack.pop().toString());
                        opr = (char)stack.pop();
                        tmp2 = Float.parseFloat(stack.pop().toString());
                        res = Operate(tmp2,tmp1,opr);
                        stack.push(res);
                        if (state == 1)
                            state = 0;
                        else // state == 3
                            state = 2;
                    } 
                    if (c == '+' || c =='-') 
                    {
                        if (!minusstate)
                        {
                            if (stack.peek().toString().charAt(0) == '(')
                                minusstate = true;
                        }
                        else
                        {
                            minusstate = false;
                        }
                        
                        if (state == 2)
                        {
                            tmp1 = Float.parseFloat(stack.pop().toString());
                            opr = (char)stack.pop();
                            tmp2 = Float.parseFloat(stack.pop().toString());
                            res = Operate(tmp2,tmp1,opr);
                            stack.push(res);
                            state = 0;
                        }
                        
                        if (state == 0)
                        {
                            stack.push(c);
                            state = 2;
                        }
                    }
                    else if (c == '*' || c =='/')
                    {
                        if (state == 0)
                        {
                            stack.push(c);
                            state = 1;
                        }
                        else if (state == 2)
                        {
                            stack.push(c);
                            state = 3;
                        }
                    }
                }
                
                if (c == '(')
                {
                    state = 0;
                    stack.push(c);
                }
                else if (c == ')')
                {
                    if (minusstate)
                    {
                        res = Float.parseFloat(stack.pop().toString());
                        opr = (char)stack.pop();
                        if (opr == '-')
                            res = -res;
                        stack.pop();
                        minusstate = false;
                    }
                    else
                    {
                        while (stack.peek().toString().charAt(0) != '(')
                        {
                            tmp1 = Float.parseFloat(stack.pop().toString());
                            if (stack.peek().toString().charAt(0) != '(')
                            {
                                opr = (char)stack.pop();
                                tmp2 = Float.parseFloat(stack.pop().toString());
                                res = Operate(tmp2,tmp1,opr);
                            }
                            else
                            {
                                res = tmp1;
                            }
                            if (stack.peek().toString().charAt(0)!='(')
                                stack.push(res);
                        } 
                        stack.pop();
                    }
                    
                    if (!stack.isEmpty())
                    {
                        if (stack.peek().toString().charAt(0) == '*' || stack.peek().toString().charAt(0) == '/')
                        {
                            state = 1;
                        }
                        else if (stack.peek().toString().charAt(0) == '+' || stack.peek().toString().charAt(0) == '-')
                        {
                            state = 2;
                        }
                        else
                        {
                            state = 0;
                        }
                    }
                    else
                    {
                        state = 0;
                    }
                    stack.push(res);
                }
            }
            else // char selain operator
            {
                tmps.append(c);
            }
        }
        
        if (tmps.length() > 0)
        {
            stack.push(tmps.toString());
        }
        
       while (!stack.isEmpty())
       {
           res = Float.parseFloat(stack.pop().toString());
           if (!stack.isEmpty())
           {
               tmp1 = res;
               opr = (char)stack.pop();
               tmp2 = Float.parseFloat(stack.pop().toString());
               res = Operate(tmp2,tmp1,opr);
               stack.push(res);
           } 
       }  
        
       return res;    
    }
    
    
    private float Operate(float opr1, float opr2, char sign)
    {
        if (sign == '-')
            return opr1 - opr2;
        else if (sign == '+')
            return opr1 + opr2;
        else if (sign == '*')
            return opr1 * opr2;
        else if (sign == '/')
            return opr1 / opr2;
        else
            return -99;
    }
}
