package com.zeher.orecraft.transfer.container.pipe;

import com.zeher.trzlib.client.slot.TRZSlotFake;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPoweredEnergyPipeDirection
  extends Container
{
  private IInventory capacitor;
  private int power;
  
  public ContainerPoweredEnergyPipeDirection(InventoryPlayer invPlayer, IInventory tile)
  {
    this.capacitor = tile;
    addSlotToContainer(new TRZSlotFake(tile, 0, 11,16));
    addSlotToContainer(new TRZSlotFake(tile, 1, 123,16));
    addSlotToContainer(new TRZSlotFake(tile, 2, 11,46));
    addSlotToContainer(new TRZSlotFake(tile, 3, 123,46));
    addSlotToContainer(new TRZSlotFake(tile, 4, 11,76));
    addSlotToContainer(new TRZSlotFake(tile, 5, 123,76));

  }
  
  public ItemStack transferStackInSlot(EntityPlayer player, int index) {
	  ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.inventorySlots.get(index);

      if (slot != null && slot.getHasStack())
      {
          ItemStack itemstack1 = slot.getStack();
          itemstack = itemstack1.copy();

          if (index < this.capacitor.getSizeInventory())
          {
              if (!this.mergeItemStack(itemstack1, this.capacitor.getSizeInventory(), this.inventorySlots.size(), true))
              {
                  return ItemStack.EMPTY;
              }
          }
          else if (!this.mergeItemStack(itemstack1, 0, this.capacitor.getSizeInventory(), false))
          {
              return ItemStack.EMPTY;
          }

          if (itemstack1.isEmpty())
          {
              slot.putStack(ItemStack.EMPTY);
          }
          else
          {
              slot.onSlotChanged();
          }
      }

      return itemstack;
	}
  
  public boolean canInteractWith(EntityPlayer entityplayer)
  {
    return this.capacitor.isUsableByPlayer(entityplayer);
  }
  
}
