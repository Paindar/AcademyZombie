package cn.paindar.academymonster.entity.ai;

import cn.lambdalib.util.mc.BlockSelectors;
import cn.lambdalib.util.mc.EntitySelectors;
import cn.lambdalib.util.mc.Raytrace;
import cn.paindar.academymonster.ability.AIThunderClap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MovingObjectPosition;

/**
 * Created by Paindar on 2017/2/23.
 */
public class EntityAIThunderClap extends EntityAIBase
{
    private final EntityLiving speller;
    private EntityLivingBase target;
    private AIThunderClap skill;

    public EntityAIThunderClap(EntityLiving speller,AIThunderClap skill)
    {
        this.speller=speller;
        this.skill=skill;
    }

    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase target=speller.getAttackTarget();
        if (target==null||skill.isSkillInCooldown())
            return false;
        double dist=speller.getDistanceSqToEntity(target);
        return dist <= skill.getMaxDistance() * skill.getMaxDistance();
    }

    public void startExecuting()
    {
        this.target =this.speller.getAttackTarget();
    }

    public void resetTask()
    {
        this.target = null;
    }

    public void updateTask()
    {
        if(!skill.isSkillInCooldown()&&target!=null)
        {
            MovingObjectPosition result= Raytrace.rayTraceBlocks(speller.worldObj,speller.getPosition(1), target.getPosition(1), BlockSelectors.filNormal);
            if(result==null) skill.spell(target.posX,target.posY,target.posZ);
        }

    }
}
