package ruukas.infinity.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ruukas.infinity.Infinity;
import ruukas.infinity.data.InfinityConfig;
import ruukas.infinity.data.thevoid.VoidController;

public abstract class InfinityTab extends CreativeTabs
{
    public InfinityTab(String label) {
        super( label );
    }
    
    public InfinityTab(int index, String label) {
        super( index, label );
    }
    
    public static void initTabs()
    {
        int tabs = 7
                - (InfinityConfig.getIsVoidEnabled() ? 0 : 1)
                - (InfinityConfig.getIsUnavailableTabEnabled() ? 0 : 1)
                - (InfinityConfig.getIsBannerTabEnabled() ? 0 : 1)
                - (InfinityConfig.getIsHeadTabEnabled() ? 0 : 1)
                - (InfinityConfig.getIsThiefTabEnabled() ? 0 : 1)
                - (InfinityConfig.getIsFireworkTabEnabled() ? 0 : 1);
        
        int id = getNextID();
        
        boolean successRealm = false;
        int foundId = id;
        
        for ( int i = foundId ; i < foundId + tabs ; i++ )
        {
            if ( (i - 16) % 5 == 0 )
            {
                foundId = i;
                successRealm = true;
                break;
            }
        }
        
        Infinity.REALM = new InfinityTabRealm( successRealm ? foundId : id++, "realm", successRealm );
        
        if ( InfinityConfig.getIsUnavailableTabEnabled() )
        {
            Infinity.UNAVAILABLE = new InfinityTab( id >= foundId ? 1 + id++ : id++, "unavailable" ) {
                @Override
                public ItemStack getTabIconItem()
                {
                    return new ItemStack( Blocks.BARRIER );
                }
                
                @Override
                public void displayAllRelevantItems( NonNullList<ItemStack> stackList )
                {
                    super.displayAllRelevantItems( stackList );
                    
                    stackList.add( new ItemStack( Items.SPAWN_EGG ) );
                    stackList.add( new ItemStack( Items.POTIONITEM ) );
                    stackList.add( new ItemStack( Items.SPLASH_POTION ) );
                    stackList.add( new ItemStack( Items.LINGERING_POTION ) );
                    stackList.add( new ItemStack( Items.TIPPED_ARROW ) );
                    stackList.add( new ItemStack( Items.ENCHANTED_BOOK ) );
                }
            };
        }
        
        if ( InfinityConfig.getIsBannerTabEnabled() )
        {
            Infinity.BANNERS = new InfinityTabBanners( id >= foundId ? 1 + id++ : id++ );
        }
        
        if ( InfinityConfig.getIsHeadTabEnabled() )
        {
            Infinity.SKULLS = new InfinityTabSkulls( id >= foundId ? 1 + id++ : id++ );
        }
        
        if ( InfinityConfig.getIsThiefTabEnabled() )
        {
            Infinity.THIEF = new InfinityTabThief( id >= foundId ? 1 + id++ : id++ );
        }
        
        if ( InfinityConfig.getIsFireworkTabEnabled() )
        {
            Infinity.FIREWORKS = new InfinityTabFireworks( id >= foundId ? 1 + id++ : id++ );
        }
        
        if ( InfinityConfig.getIsVoidEnabled() )
        {
            Infinity.VOID = new InfinityTab( id >= foundId ? 1 + id++ : id++, "void" ) {
                @Override
                public ItemStack getTabIconItem()
                {
                    return new ItemStack( Blocks.STAINED_GLASS, 1, 15 );
                }
                
                @Override
                public void displayAllRelevantItems( NonNullList<ItemStack> stackList )
                {
                    super.displayAllRelevantItems( stackList );
                    
                    VoidController.loadVoidToList( stackList );
                }
                
                @Override
                public boolean hasSearchBar()
                {
                    return true;
                }
            };
        }
    }
}
