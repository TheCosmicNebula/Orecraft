package com.zeher.orecraft.client.slot;

import com.zeher.orecraft.core.handlers.recipe.MachineCombinerCraftingHandler;
import com.zeher.orecraft.core.handlers.recipe.MachineGrinderRecipes;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class SlotMachineCombiner
  extends Slot
{
  private EntityPlayer thePlayer;
  private int field_75228_b;
  
  public SlotMachineCombiner(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
  {
    super(par2IInventory, par3, par4, par5);
    this.thePlayer = par1EntityPlayer;
  }
  
  public boolean isItemValid(ItemStack par1ItemStack)
  {
    return false;
  }
  
  public ItemStack decrStackSize(int par1)
  {
    if (this.getHasStack()) {
      this.field_75228_b += Math.min(par1, this.getStack().getCount());
    }
    return super.decrStackSize(par1);
  }
  
  public ItemStack onTake(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
  {
    this.onCrafting(par2ItemStack);
    super.onTake(par1EntityPlayer, par2ItemStack);
    return par2ItemStack;
  }
  
  protected void onCrafting(ItemStack par1ItemStack, int par2)
  {
    this.field_75228_b += par2;
    this.onCrafting(par1ItemStack);
  }
  
  protected void onCrafting(ItemStack stack)
  {
    stack.onCrafting(this.thePlayer.world, this.thePlayer, this.field_75228_b);
    if (!this.thePlayer.world.isRemote)
    {
      int i = this.field_75228_b;
      float f = 1;
      if (f == 0.0F)
      {
        i = 0;
      }
      else if (f < 1.0F)
      {
        int j = MathHelper.floor(i * f);
        if ((j < MathHelper.ceil(i * f)) && ((float)Math.random() < i * f - j)) {
          j++;
        }
        i = j;
      }
      while (i > 0)
      {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.thePlayer.world.spawnEntity(new EntityXPOrb(this.thePlayer.world, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, j));
      }
    }
    this.field_75228_b = 0;
  }
}
