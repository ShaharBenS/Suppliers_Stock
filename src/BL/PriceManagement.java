package BL;

import DAL.Product_Data;

public class PriceManagement
{
   private Product_Data PD;

   public PriceManagement(Product_Data pd)
   {
       this.PD = pd;
   }


   /* DATA FOR PRICES */
   public boolean updateBuyPrice(String line)
   {
       String[] prop = line.split("\\s+");
       if(prop.length != 2) return false;
       try{
           int num1 = Integer.parseInt(prop[0]);
           int num2 = Integer.parseInt(prop[1]);
           return PD.updateBuyPrice(num1, num2);
       } catch(Exception e){ return  false; }
   }

   public boolean updateSellPrice(String line)
   {
       
       String[] prop = line.split("\\s+");
       if(prop.length != 2) return false;
       try{
           int num1 = Integer.parseInt(prop[0]);
           int num2 = Integer.parseInt(prop[1]);
           return PD.updateSellPrice(num1, num2);
       } catch(Exception e){ return  false; }
   }

   public boolean addDiscount(String line)
   {
       String[] prop = line.split("\\s+");
       if(prop.length != 4) return false;
       try{
           int id = Integer.parseInt(prop[0]);
           if(prop[0].length() == 6)
           {
               int disc = Integer.parseInt(prop[1]);
               String[] start = prop[2].split("\\.");
               String[] end = prop[3].split("\\.");
               if(start.length != 3 || end.length != 3) return false;
               SharedClasses.Date Dstart = new SharedClasses.Date(Integer.parseInt(start[2]),Integer.parseInt(start[1]),Integer.parseInt(start[0]));
               SharedClasses.Date Dend = new SharedClasses.Date(Integer.parseInt(end[2]),Integer.parseInt(end[1]),Integer.parseInt(end[0]));
               return (PD.updateProductDiscount(id, disc) && PD.updateStartDate(id,Dstart) && PD.updateEndDate(id, Dend));
           }
           else if(prop[0].length() == 3)
           {
               int disc = Integer.parseInt(prop[1]);
               String[] start = prop[2].split("\\s+");
               String[] end = prop[3].split("\\s+");
               if(start.length != 3 || end.length != 3) return false;
               SharedClasses.Date Dstart = new SharedClasses.Date(Integer.parseInt(start[2]),Integer.parseInt(start[1]),Integer.parseInt(start[0]));
               SharedClasses.Date Dend = new SharedClasses.Date(Integer.parseInt(end[2]),Integer.parseInt(end[1]),Integer.parseInt(end[0]));
               return (PD.updateCategoryDiscount(id, disc ,Dstart,Dend));
           }
           else return false;
       } catch(Exception e){ return  false; }
   }
}
