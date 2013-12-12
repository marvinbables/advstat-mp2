package view.util;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class Util
{
    
    public enum ErrorMessage {
        INPUT_NUMBERS_ONLY, COEFFICIENT_FORMAT, INPUT_PAIRS_FORMAT
    }

    /**
     * This function verifies whether the input 
     * contains only real numbers and spaces. 
     * 
     * Revision: What was the point of having an 
     * if statement and a parameter saying
     * "numbers only" if that is the only
     * input checking you are going to check?
     * 
     *   Revised by Darren
     *   
     *   
     * @param field
     * @return
     */
    public static boolean isInputNumerical(String field)
    {
        try
        {
            String[] split = field.split(" ");
            for (String string : split)
            {
                // If each term can be parsed
                // into float, then number
                float f = Float.parseFloat(string);
            }
            return true;
        }
        catch (Exception e)
        {
            // Else, it has error
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * This method automatically marks text components as 
     * pink when incorrect, and white when correct.
     * @param component
     * @return
     */
    public static boolean isInputNumerical(JTextComponent component){
        boolean inputNumerical = isInputNumerical(component.getText());
        if (inputNumerical == false) component.setBackground(Color.pink);
        else component.setBackground(Color.white);
        return inputNumerical;
    }
    
    public static boolean hasInput(JTextComponent component){
        String text = component.getText();
        text = text.trim();
        component.setText(text);
        
        if (text.length() > 0) {
            component.setBackground(Color.white);            
            return true;
        }
        
        component.setBackground(Color.pink);
        return false;
    }
    

    /**
     * Displays an error message
     * @param s
     */
    public static void Error(String s)
    {
        JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void Error(ErrorMessage e){
        String s = "I'm sorry, something did not work. We have expert monkeys to fix the problem.";
        switch(e){
        case INPUT_NUMBERS_ONLY:
            s = "Incorrect format: Please input numbers only.";
            break;
        case COEFFICIENT_FORMAT:
            s = "Incorrect format: Please input coefficient exponent pairs (e.g. 1 2 2 4)";
            break;
        case INPUT_PAIRS_FORMAT:
            s = "Incorrect format: The input should be pairs.";
            break;
        default:
            s = "";
            break;
        
        }
        Error(s);
    }
}
